<section class="login_box_area section-margin">
	<div class="container">
		<div class="row">
			<div class="col-lg-6">
				<div class="login_box_img">
					<div class="hover">
						<h4>Forget your password ?</h4>
						<p>If yout forget your password then enter your verified email
							address. And recover your account.</p>
						<a class="button button-account" href="userLogin">Go Back To
							Login</a>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="login_form_inner">
					<h3>Recover Account By Email</h3>
					<form:form class="row login_form" action="emailValidation">
						<div class="col-md-12 form-group">
							<input type="email" class="form-control" name="userEmail"
								placeholder="Enter your valid email"
								onfocus="this.placeholder = ''"
								onblur="this.placeholder = 'Enter your valid email'" />
						</div>
						<div class="col-md-12 form-group">
							<button type="submit" value="submit"
								class="button button-login w-100">Use Email</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</section>