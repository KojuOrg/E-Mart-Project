<div class="row">
	<div class="col-sm-6"></div>
	<div class="col-sm-6">
		<table class="table table-hover table-striped">
			<tr>
				<th>Product Name</th>
				<td>${product.category}</td>
			</tr>
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
<div class="row">
	<div class="col-sm-4">
		<h3>Product Specification</h3>
	</div>
	<div class="col-sm-4">
		<h3>Seller's Details</h3>
	</div>
	<div class="col-sm-4">
		<h3>Reporter's Details</h3>
	</div>
</div>



