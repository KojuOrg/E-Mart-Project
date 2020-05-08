package configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity  
@ComponentScan({"com.koju"})  
@EnableGlobalMethodSecurity(prePostEnabled=true)  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	
	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
    @Override  
    protected void configure(HttpSecurity http) throws Exception {            
    	http
    	.csrf().disable()
    	.authorizeRequests()  
        .antMatchers("/admin").authenticated() 
        .antMatchers("/adminUpdateCredentials").authenticated()  
        .antMatchers("/userFeedback").authenticated() 
        .antMatchers("/deleteUserFeedback").authenticated() 
        .antMatchers("/reportedProducts").authenticated() 
        .antMatchers("/viewReportedProduct").authenticated() 
        .antMatchers("/delReportedProduct").authenticated() 
        .antMatchers("/recoveryEmail").permitAll()
        .antMatchers("/checkRecoveryCode").permitAll()  
        .antMatchers("/setNewPass").permitAll()
        .antMatchers("/adminLogout").permitAll()
        .antMatchers("/adminLogin").permitAll()
        .antMatchers("/","/index","/contact","/userLogin","/loginUser","/forgetPassword","/emailValidation",
        		"/codeValidation",
        		"/recoverAccount",
        		"/electronics",
        		"/computers",
        		"/mobiles",
        		"/clothes",
        		"/cosmetics",
        		"/others",
        		"/singleProduct",
        		"/sellProduct",
        		"/sellingProduct",
        		"/paymentSuccess",
        		"/paymentFailure",
        		"/singleProduct",
        		"/logoutUser",
        		"/userFeedback",
        		"/productReport",
        		"/register",
        		"/processRegister",
        		"/confirmYourEmail",
        		"/addComment").permitAll()
        .and()  
        .formLogin()  
        .loginPage("/login")  
        .failureHandler(customAuthenticationFailureHandler)
        .and()  
        .logout()  
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/admin");  
    }  
}
