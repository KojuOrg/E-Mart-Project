<!--================Single Product Area =================-->
<div class="product_image_area">
	<div class="container">
		<div class="row s_product_inner">
			<div class="col-lg-6">
				<div class="owl-carousel owl-theme s_Product_carousel">
					<div class="single-prd-item">
						<img class="img-fluid"
							src="data:image/png;image/jpg;image/jpeg;image/gif;base64,${product.photo1}"
							alt="Image Error">
					</div>
					<div class="single-prd-item">
						<img class="img-fluid"
							src="data:image/png;image/jpg;image/jpeg;image/gif;base64,${product.photo2}"
							alt="Image Error">
					</div>
					<div class="single-prd-item">
						<img class="img-fluid"
							src="data:image/png;image/jpg;image/jpeg;image/gif;base64,${product.photo3}"
							alt="Image Error">
					</div>
				</div>
			</div>
			<div class="col-lg-5 offset-lg-1">
				<div class="s_product_text">
					<h3>${product.productName}</h3>
					<h2>Rs.${product.price}</h2>
					<table class="table">
						<tr>
							<th>Category</th>
							<td>${product.category}</td>
						</tr>
						<tr>
							<th>Used For</th>
							<td>${product.usedFor}</td>
						</tr>
						<tr>
							<th>Condition</th>
							<td><c:choose>
									<c:when test="${product.productCondition eq 1}">
										Normal
									</c:when>
									<c:when test="${product.productCondition eq 2}">
										Good
									</c:when>
									<c:when test="${product.productCondition eq 3}">
										Very Good
									</c:when>
									<c:when test="${product.productCondition eq 4}">
										Excellent
									</c:when>
								</c:choose></td>
						</tr>
						<tr>
							<th>Price Negotiable</th>
							<td><c:choose>
									<c:when test="${product.priceNegotiable eq true}">
										Yes
									</c:when>
									<c:when test="${product.priceNegotiable eq false}">
										No
									</c:when>
								</c:choose></td>
						</tr>
						<tr>
							<th>Warranty Period</th>
							<td>${product.warrantyPeriod}</td>
						</tr>
						<tr>
							<th>Delivery Area</th>
							<td>${product.deliveryArea}</td>
						</tr>
						<tr>
							<th>Delivery Charges</th>
							<td>Rs. ${product.deliveryCharges}</td>
						</tr>
						<tr>
							<th>Uploaded In</th>
							<td>${product.regDate}</td>
						</tr>
						<tr>
							<th style="color: red">Expired In</th>
							<td>${product.delDate}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!--================End Single Product Area =================-->

<!--================Product Description Area =================-->
<section class="product_description_area">
	<div class="container">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item"><a class="nav-link" id="seller-tab"
				data-toggle="tab" href="#seller" role="tab" aria-controls="seller"
				aria-selected="true">Seller's Info</a></li>
			<li class="nav-item"><a class="nav-link" id="profile-tab"
				data-toggle="tab" href="#profile" role="tab" aria-controls="profile"
				aria-selected="false">Specification</a></li>
			<li class="nav-item"><a class="nav-link" id="contact-tab"
				data-toggle="tab" href="#contact" role="tab" aria-controls="contact"
				aria-selected="false">Comments</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade" id="seller" role="tabpanel"
				aria-labelledby="seller-tab">
				<div class="table-responsive">
					<table class="table">
						<tbody>
								<tr>
									<td>
										<h5>Name</h5>
									</td>
									<td>
										<h5>${seller.fullName}</h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5>Email</h5>
									</td>
									<td>
										<h5>${seller.email}</h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5>Contact No</h5>
									</td>
									<td>
										<h5>${seller.contactNum}</h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5>Registered Date</h5>
									</td>
									<td>
										<h5>${seller.regDate}</h5>
									</td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel"
				aria-labelledby="profile-tab">
				<div class="table-responsive">
					<table class="table">
						<tbody>
							<c:forEach items="${specification}" var="spec">
								<tr>
									<td>
										<h5>${spec.key}</h5>
									</td>
									<td>
										<h5>${spec.value}</h5>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel"
				aria-labelledby="contact-tab">
				<div class="row">
					<div class="col-lg-6">
						<div class="comment_list">
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="img/product/review-1.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									sed do eiusmod tempor incididunt ut labore et dolore magna
									aliqua. Ut enim ad minim veniam, quis nostrud exercitation
									ullamco laboris nisi ut aliquip ex ea commodo</p>
							</div>
							<div class="review_item reply">
								<div class="media">
									<div class="d-flex">
										<img src="img/product/review-2.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									sed do eiusmod tempor incididunt ut labore et dolore magna
									aliqua. Ut enim ad minim veniam, quis nostrud exercitation
									ullamco laboris nisi ut aliquip ex ea commodo</p>
							</div>
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="img/product/review-3.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									sed do eiusmod tempor incididunt ut labore et dolore magna
									aliqua. Ut enim ad minim veniam, quis nostrud exercitation
									ullamco laboris nisi ut aliquip ex ea commodo</p>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="review_box">
							<h4>Post a comment</h4>
							<form class="row contact_form" action="contact_process.php"
								method="post" id="contactForm" novalidate="novalidate">
								<div class="col-md-12">
									<div class="form-group">
										<input type="text" class="form-control" id="name" name="name"
											placeholder="Your Full name">
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<input type="email" class="form-control" id="email"
											name="email" placeholder="Email Address">
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<input type="text" class="form-control" id="number"
											name="number" placeholder="Phone Number">
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<textarea class="form-control" name="message" id="message"
											rows="1" placeholder="Message"></textarea>
									</div>
								</div>
								<div class="col-md-12 text-right">
									<button type="submit" value="submit" class="btn primary-btn">Submit
										Now</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--================End Product Description Area =================-->