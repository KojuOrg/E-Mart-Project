<section class="section-margin calc-60px">
	<div class="container">
		<div class="section-intro pb-60px">
			<h2>
				Cosmetics <span class="section-intro__style">Section</span>
			</h2>
		</div>
		<div class="row">
			<c:forEach items="${categoryProducts}" var="product">
				<div class="col-md-6 col-lg-4 col-xl-3">
					<div class="card text-center card-product">
						<div class="card-product__img">
							<img class="card-img" style="height: 170px; width: 230px"
								src="data:image/jpg;image/png;image/jpeg;image/gif;base64,${product.photo1}"
								alt="Image Error">
							<ul class="card-product__imgOverlay">
								<li><button>
										<i class="ti-search"></i>
									</button></li>
								<li><button>
										<i class="ti-shopping-cart"></i>
									</button></li>
								<li><button>
										<i class="ti-heart"></i>
									</button></li>
							</ul>
						</div>
						<div class="card-body">
							<p>Cosmetics</p>
							<h4 class="card-product__title">
								<a href="single-product.html">${product.productName}</a>
							</h4>
							<p class="card-product__price">Rs. ${product.price}</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>