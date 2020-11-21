package beans;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import services.UserEmailValidation;

public class UserEmailValidator implements
ConstraintValidator<UniqueUserEmail, String> {

	//@Autowired
	//UserNameValidation unvserv;
	
  @Override
  public void initialize(UniqueUserEmail userEmail) {
  }

  @Override
  public boolean isValid(String userEmail,
    ConstraintValidatorContext cxt) {
	  
	  return new UserEmailValidation().unique(userEmail);
	  
    //  return contactField != null && contactField.matches("[0-9]+")
     //   && (contactField.length() > 8) && (contactField.length() < 14);
  }

}
