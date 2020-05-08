package configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import services.AdminService;

@Component
public class CustomApplicationListener implements ApplicationListener<ApplicationEvent> {
	@Autowired
	private AdminService adminService;
	
	@Override
	public void onApplicationEvent(ApplicationEvent appEvent)
	{
	    if (appEvent instanceof InteractiveAuthenticationSuccessEvent)
	    {
	        InteractiveAuthenticationSuccessEvent event = (InteractiveAuthenticationSuccessEvent) appEvent;
	        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
	        adminService.resetInvalidCount(userDetails.getUsername());
	    }
	}
	
}
