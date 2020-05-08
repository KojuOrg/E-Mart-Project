package services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import beans.Message;
import beans.Product;
import beans.UpdateUser;
@Service
public class ProductService {
	@Autowired 
	private Product product;
	@Autowired
	private Message message;
	@Autowired
	private UpdateUser user;
	@Autowired
	private CrossSiteFilter filter;
	
	private SessionFactory factory;
	private Session session;
	private List<String> titles;
	private List<String> descriptions;
	private void initValues() {
		this.factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		this.session = this.factory.getCurrentSession();
		//this.message = new Message();
	}
	public void setProduct(Product product) {
		this.initValues();
		this.product = product;
	}
	private  byte[] compressImage(CommonsMultipartFile file,String path,String filename) {
		try {
			File originalImage = new File(file.getOriginalFilename());
			File compressedImage = new File(path+File.separator+filename);
			originalImage.createNewFile();
			FileOutputStream fos = new FileOutputStream(originalImage);
			fos.write(file.getBytes());
			fos.close();
			
			BufferedImage tmpImage = ImageIO.read(originalImage);
            BufferedImage result = new BufferedImage(
                    (int)(tmpImage.getWidth()/1.5),
                    (int)(tmpImage.getHeight()/1.5),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(tmpImage, 0, 0,(int)(tmpImage.getWidth()/1.5),(int)(tmpImage.getHeight()/1.5), Color.WHITE, null);
            ImageIO.write(result, "jpg", originalImage);
            
			
			BufferedImage image = ImageIO.read(originalImage);
			OutputStream os = new FileOutputStream(compressedImage);

			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = (ImageWriter) writers.next();

			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();

			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.2f);
			writer.write(null, new IIOImage(image, null, null), param);

			os.close();
			ios.close();
			writer.dispose();
			
			byte[] bytesArray = new byte[(int)compressedImage.length()];
			FileInputStream fin = new FileInputStream(compressedImage);
			fin.read(bytesArray);
			fin.close();
			
			return bytesArray;
		} catch (Exception er) {
			System.out.println("Error : " + er.getMessage());
			return null;
		}
	}
	public Message registerProduct(CommonsMultipartFile photo1,CommonsMultipartFile photo2,CommonsMultipartFile photo3,HttpSession session) {
		try {
			ServletContext context = session.getServletContext();
			String path = context.getRealPath("uploads");
			File folder = new File(path);
			if(!folder.exists()) {
				folder.mkdir();
			}
			String photo1Name = photo1.getOriginalFilename();
			String photo2Name = photo2.getOriginalFilename();
			String photo3Name = photo3.getOriginalFilename();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
			String date = formatter.format(new Date());
			photo1Name = date+"1"+(int)(Math.random()*9999999+1)+"@"+photo1Name;
			photo2Name = date+"2"+(int)(Math.random()*9999999+1)+"@"+photo2Name;
			photo3Name = date+"3"+(int)(Math.random()*9999999+1)+"@"+photo3Name;
			
			byte[] bytes1 = this.compressImage(photo1, path, photo1Name);
			byte[] bytes2 = this.compressImage(photo2, path, photo2Name);
			byte[] bytes3 = this.compressImage(photo3, path, photo3Name);
			
//			byte[] bytes1 = photo1.getBytes();
//			byte[] bytes2 = photo2.getBytes();
//			byte[] bytes3 = photo3.getBytes();
			
//			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo1Name)));
//			stream.write(bytes1);
//			stream.flush();
//			stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo2Name)));
//			stream.write(bytes2);
//			stream.flush();
//			stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+photo3Name)));
//			stream.flush();
//			stream.close();
			
			this.product.setPhoto1File(bytes1);
			this.product.setPhoto2File(bytes2);
			this.product.setPhoto3File(bytes3);
			this.product.setPhoto1(photo1Name);
			this.product.setPhoto2(photo2Name);
			this.product.setPhoto3(photo3Name);
			
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			this.product.setRegDate(formatter.format(new Date()));
			
			this.product.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
			this.product.setNoOfViews(0);
			
			this.session.beginTransaction();
			this.session.save(this.product);
			this.session.getTransaction().commit();
			
			this.factory = new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(UpdateUser.class)
					.buildSessionFactory();
			this.session = this.factory.getCurrentSession();
			this.session.beginTransaction();
			this.user = (UpdateUser)(this.session.get(UpdateUser.class,Integer.parseInt(session.getAttribute("userId").toString())));
			this.user.setNumOfUploadedItem(this.user.getNumOfUploadedItem()+1);
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Your Product is now available in selling list.");
		}catch(Exception er) {
			this.message.setStatus(true);
			this.message.setMessage("Internal Error ..!!!");
			System.out.println("Register product Error : "+er.getMessage());
		}
		return this.message;
	}
	public Product getSingleProduct(int id) {
		try {
			this.initValues();
			this.session.beginTransaction();
			this.product = new Product();
			this.product = (Product)(this.session.get(Product.class,id));
			this.product.setNoOfViews(this.product.getNoOfViews()+1);
			this.session.getTransaction().commit();
			this.product.setPhoto1(Base64.getEncoder().encodeToString(this.product.getPhoto1File()));
			this.product.setPhoto2(Base64.getEncoder().encodeToString(this.product.getPhoto2File()));
			this.product.setPhoto3(Base64.getEncoder().encodeToString(this.product.getPhoto3File()));
		}catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
			this.product = null;
		}
		return this.product;
	}
	public Map getProductSpecifications() {
		String[] specification = this.product.getProductSpecification().split("%main%");
		String[] titles = specification[0].split("%sub%");
		String[] desc = specification[1].split("%sub%");
		Map<String,String> spec = new HashMap<String,String>();
		for(int i=0;i<titles.length;i++) {
			spec.put(titles[i],desc[i]);
		}
		return spec;
	}
	public Message deleteProduct(int id) {
		this.initValues();
		try {
			this.session.beginTransaction();
			this.product = (Product)this.session.get(Product.class, id);
			this.product.setStatus(true);
			this.product.setDelDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			this.session.getTransaction().commit();
			this.message.setStatus(false);
			this.message.setMessage("Product and its report deleted successfully.");
		}catch(Exception er) {
			System.out.println("Error Detected : "+er.getMessage());
			this.message.setStatus(true);
			this.message.setMessage("Internal errorr.!!!");
		}
		return this.message;
	}
	
	public Message checkXssAttacks(Product product) {
		if(this.filter.isHtml(product.getProductName())) {
			this.message.setStatus(true);
			this.message.setMessage("Cannot input HTML character in Product Name");
		}
		else {
			if(this.filter.isHtml(product.getDeliveryArea())) {
				this.message.setStatus(true);
				this.message.setMessage("Cannot input HTML character in Delivery Area");
			}
			else {
				if(this.filter.isHtml(product.getProductSpecification())) {
					this.message.setStatus(true);
					this.message.setMessage("Cannot input HTML character in Product Specification");
				}
				else {
					if(this.filter.isHtml(product.getUsedFor())) {
						this.message.setStatus(true);
						this.message.setMessage("Cannot input HTML character in Used For");
					}
					else {
						if(this.filter.isHtml(product.getWarrantyPeriod())) {
							this.message.setStatus(true);
							this.message.setMessage("Cannot input HTML character in Warranty Period");
						}
						else {
							if(this.filter.isHtml(product.getDeliveryCharges().toString())) {
								this.message.setStatus(true);
								this.message.setMessage("Cannot input HTML character in Delivery Charges");
							}
							else {
								if(this.filter.isHtml(product.getPhoto1())) {
									this.message.setStatus(true);
									this.message.setMessage("Rename your first image without HTML character then upload.");
								}
								else {
									if(this.filter.isHtml(product.getPhoto2())) {
										this.message.setStatus(true);
										this.message.setMessage("Rename your second image without HTML character then upload.");
									}
									else {
										if(this.filter.isHtml(product.getPhoto3())) {
											this.message.setStatus(true);
											this.message.setMessage("Rename your third image without HTML character then upload.");
										}
										else {
											this.message.setStatus(false);
											this.message.setMessage("Vaid Product");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return this.message;
	}
	
}
