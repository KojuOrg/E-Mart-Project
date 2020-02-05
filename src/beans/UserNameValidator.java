package beans;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//import org.springframework.beans.factory.annotation.Autowired;

import services.UserNameValidation;

public class UserNameValidator implements
ConstraintValidator<UniqueUsername, String> {

	//@Autowired
	//UserNameValidation unvserv;
	
  @Override
  public void initialize(UniqueUsername userName) {
  }

  @Override
  public boolean isValid(String userName,
    ConstraintValidatorContext cxt) {
	  
	  return new UserNameValidation().unique(userName);
	  
    //  return contactField != null && contactField.matches("[0-9]+")
     //   && (contactField.length() > 8) && (contactField.length() < 14);
  }

}
