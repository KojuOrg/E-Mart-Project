package configurations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import services.AdminService;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	AdminService adminService;
	
	private void handleBadCredentials(String username) {
		adminService.adminLogin(username);
	}
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		switch(exception.getMessage()) {
		case "Bad credentials":
			session.setAttribute("loginError","Invlid Username or Password.!!");
			this.handleBadCredentials(request.getParameter("username").toString());
			break;
			
		case "User account is locked":
			session.setAttribute("loginError", "Due to multiple invalid access your account is locked. Please go through forget password method to recover your account.");
			break;
		}
		response.sendRedirect("http://localhost:8080/Emart/login");
	}
}
