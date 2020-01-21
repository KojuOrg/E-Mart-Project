<section class="product_description_area">
	<div class="container">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item">
				<h4>Please Provide Correct Information. All Fields Are Required
					Except Warranty</h4>
			</li>
		</ul>
		<form:form action="sellingProduct" method="post"
			modelAttribute="product" enctype="multipart/form-data"
			class="form-contact form-review mt-3">
			<div class="tab-content" id="myTabContent">
				<div class="row">
					<div class="col-lg-6">
						<div class="form-group">
							<form:errors path="productName" cssClass="error"></form:errors>
							<form:input path="productName" class="form-control" type="text"
								placeholder="Enter Product's Name" />
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-lg-6">
									<h6>Category :</h6>
								</div>
								<div class="col-lg-6">
									<form:errors path="category" cssClass="error"></form:errors>
									<form:select path="category" class="form-control select">
										<option>Computers</option>
										<option>Mobiles</option>
										<option>Cosmetics</option>
										<option>Electronics</option>
										<option>Clothes</option>
										<option>Others</option>
									</form:select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-lg-6">
									<h6>Condition :</h6>
								</div>
								<div class="col-lg-6">
									<form:errors path="productCondition" cssClass="error"></form:errors>
									<form:select path="productCondition" class="form-control select">
										<option value="4">Excellent</option>
										<option value="3">Very Good</option>
										<option value="2">Good</option>
										<option value="1">Normal</option>
									</form:select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<form:errors path="usedFor" cssClass="error"></form:errors>
							<form:input path="usedFor" class="form-control" type="text"
								placeholder="Used For About [ eg : 2 yrs]" />
						</div>
						<div class="form-group">
							<h6>Price :</h6>
							<form:errors path="price" cssClass="error"></form:errors>
							<form:input path="price" class="form-control" type="text"
								placeholder="Price" />
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-lg-6">
									<h6>Price Negotiable :</h6>
									<form:errors path="priceNegotiable" cssClass="error"></form:errors>
								</div>
								<div class="col-lg-6">
									<div class="row">
										<div class="col-lg-6">
											Yes :
											<form:radiobutton path="priceNegotiable" value="1" />
										</div>
										<div class="col-lg-6">
											No :
											<form:radiobutton path="priceNegotiable" value="0" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<form:errors path="deliveryArea" cssClass="error"></form:errors>
							<form:input path="deliveryArea" class="form-control" type="text"
								placeholder="Delivery Area" />
						</div>
						<div class="form-group">
							<form:errors path="warrantyPeriod" cssClass="error"></form:errors>
							<form:input path="warrantyPeriod" class="form-control"
								type="text" placeholder="Warranty Period. If not then enter -" />
						</div>
						<div class="form-group">
							<h6>Delivery Charges :</h6>
							<form:errors path="deliveryCharges" cssClass="error"></form:errors>
							<form:input path="deliveryCharges" class="form-control"
								type="text" placeholder="Delivery Charges" />
						</div>
						<div class="form-group">
							<form:errors path="productSpecification" cssClass="error"></form:errors>
							<form:textarea path="productSpecification"
								class="form-control different-control w-100" name="textarea"
								id="textarea" cols="30" rows="15"
								placeholder="Product Specification" />
						</div>
						<div class="form-group text-center text-md-right mt-3">
							<button type="submit" class="button button--active button-review">Submit
								Now</button>
						</div>
					</div>
					<div class="col-lg-6">
						<h6>Upload Product Pictures</h6>
						<small style="color:green">To Make Your Product Picture Look Attractive, Please upload the Picture of size height=170px and width=230px</small>
						<div class="form-group">
							<input type="file" name="image1" onchange="firstImage(this);" required/>
						</div>
						<div class="form-group">
							<input type="file" name="image2" onchange="secondImage(this);" required/>
						</div>
						<div class="form-group">
							<input type="file" name="image3" onchange="thirdImage(this);" required/>
						</div>
						<div class="review_box">
							<div class="row total_rate">
								<div class="box_total">
									<img src="#" id="up-image1" style="display: none"
										height="200px" width="auto">
								</div>
								<div class="box_total">
									<img src="#" id="up-image2" style="display: none"
										height="200px" width="auto">
								</div>
								<div class="box_total">
									<img src="#" id="up-image3" style="display: none"
										height="200px" width="auto">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</section>
<script type="text/javascript">
	function firstImage(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#up-image1').attr('src', e.target.result);
				var img = document.getElementById("up-image1");
				img.style.display = 'block';
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
	function secondImage(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#up-image2').attr('src', e.target.result);
				var img = document.getElementById("up-image2");
				img.style.display = 'block';
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
	function thirdImage(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#up-image3').attr('src', e.target.result);
				var img = document.getElementById("up-image3");
				img.style.display = 'block';
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
</script>