package beans;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Entity
@Table(name="product")
@Component
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private int id;
	@Column(name="user_id")
	@NotNull(message="Null User Id")
	private int userId;
	@Size(min=3,max=30,message="Product Name must be between 3 to 30 character.")
	@Column(name="product_name")
	private String productName;
	@NotNull(message="Please Select the category.")
	@Column(name="category")
	private String category;
	@NotNull(message="Select the value.")
	@Column(name="product_condition")
	private byte productCondition;
	@Column(name="used_for")
	@Size(min=3,max=40,message="Must be between 3 to 40 characters.")
	private String usedFor;
	@Digits(integer=5,fraction=2,message="Decimal value must be only 2 digits. Eg : 10.25")
	@Column(name="price")
	@DecimalMin(value="0.0",inclusive=false)
	private BigDecimal price;
	@Column(name="price_negotiable")
	private boolean priceNegotiable;
	@Column(name="delivery_area")
	@Size(min=4,max=50,message="Must be between 4 to 50 characters.")
	private String deliveryArea;
	@Column(name="warranty_period")
	@Size(min=1,max=30,message="Max character is 30. If no warranty is given then fill with symbol -")
	private String warrantyPeriod;
	@Column(name="delivery_charges")
	@Digits(integer=5,fraction=2,message="Decimal value must be only 2 digits. Eg : 10.25")
	@DecimalMin(value="0.0",inclusive=true)
	private BigDecimal deliveryCharges;
	@Column(name="product_specification")
	@NotEmpty(message="Product Specification is Empty.!!!!")
	private String productSpecification;
	@Column(name="reg_date")
	private String regDate;
	@Column(name="del_date")
	private String delDate;
	@Column(name="num_of_views")
	private int noOfViews;
	@Column(name="photo1_name")
	private String photo1;
	@Column(name="photo2_name")
	private String photo2;
	@Column(name="photo3_name")
	private String photo3;
	@Column(name="photo1")
	private byte[] photo1File;
	@Column(name="photo2")
	private byte[] photo2File;
	@Column(name="photo3")
	private byte[] photo3File;
	@Column(name="status")
	private boolean status;
	public Product() {
		this.id = 0;
		this.userId = 0;
		this.productName = "";
		this.category = "";
		this.productCondition = 0;
		this.usedFor = "";
		this.price = new BigDecimal(0);
		this.priceNegotiable = false;
		this.deliveryArea = "";
		this.warrantyPeriod = "";
		this.deliveryCharges = new BigDecimal(0);
		this.productSpecification = "";
		this.regDate = "1111-11-11";
		this.delDate = "1111-11-11";
		this.noOfViews = 0;
		this.photo1 = "";
		this.photo2 = "";
		this.photo3 = "";
		this.status = false;
	}
	public Product(int id, int userId, String productName, String category, byte productCondition, String usedFor, BigDecimal price,
			boolean priceNegotiable, String deliveryArea, String warrantyPeriod, BigDecimal deliveryCharges,
			String productSpecification, String regDate, String delDate, int noOfViews, String photo1, String photo2,
			String photo3, boolean status) {
		this.id = id;
		this.userId = userId;
		this.productName = productName;
		this.category = category;
		this.productCondition = productCondition;
		this.usedFor = usedFor;
		this.price = price;
		this.priceNegotiable = priceNegotiable;
		this.deliveryArea = deliveryArea;
		this.warrantyPeriod = warrantyPeriod;
		this.deliveryCharges = deliveryCharges;
		this.productSpecification = productSpecification;
		this.regDate = regDate;
		this.delDate = delDate;
		this.noOfViews = noOfViews;
		this.photo1 = photo1;
		this.photo2 = photo2;
		this.photo3 = photo3;
		this.status = status;
	}
	public Product(Product p) {
		this.id = p.id;
		this.userId = p.userId;
		this.productName = p.productName;
		this.category = p.category;
		this.productCondition = p.productCondition;
		this.usedFor = p.usedFor;
		this.price = p.price;
		this.priceNegotiable = p.priceNegotiable;
		this.deliveryArea = p.deliveryArea;
		this.warrantyPeriod = p.warrantyPeriod;
		this.deliveryCharges = p.deliveryCharges;
		this.productSpecification = p.productSpecification;
		this.regDate = p.regDate;
		this.delDate = p.delDate;
		this.noOfViews = p.noOfViews;
		this.photo1 = p.photo1;
		this.photo2 = p.photo2;
		this.photo3 = p.photo3;
		this.status = p.status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public byte getProductCondition() {
		return productCondition;
	}
	public void setProductCondition(byte productCondition) {
		this.productCondition = productCondition;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public boolean isPriceNegotiable() {
		return priceNegotiable;
	}
	public void setPriceNegotiable(boolean priceNegotiable) {
		this.priceNegotiable = priceNegotiable;
	}
	public String getDeliveryArea() {
		return deliveryArea;
	}
	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}
	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}
	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}
	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}
	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}
	public String getProductSpecification() {
		return productSpecification;
	}
	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public int getNoOfViews() {
		return noOfViews;
	}
	public void setNoOfViews(int noOfViews) {
		this.noOfViews = noOfViews;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public byte[] getPhoto1File() {
		return photo1File;
	}
	public void setPhoto1File(byte[] photo1File) {
		this.photo1File = photo1File;
	}
	public byte[] getPhoto2File() {
		return photo2File;
	}
	public void setPhoto2File(byte[] photo2File) {
		this.photo2File = photo2File;
	}
	public byte[] getPhoto3File() {
		return photo3File;
	}
	public void setPhoto3File(byte[] photo3File) {
		this.photo3File = photo3File;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", userId=" + userId + ", productName=" + productName + ", category=" + category
				+ ", condition=" + productCondition + ", usedFor=" + usedFor + ", price=" + price + ", priceNegotiable="
				+ priceNegotiable + ", deliveryArea=" + deliveryArea + ", warrantyPeriod=" + warrantyPeriod
				+ ", deliveryCharges=" + deliveryCharges + ", productSpecification=" + productSpecification
				+ ", regDate=" + regDate + ", delDate=" + delDate + ", noOfViews=" + noOfViews + ", photo1=" + photo1
				+ ", photo2=" + photo2 + ", photo3=" + photo3 + ", status=" + status + "]";
	}
	
}
