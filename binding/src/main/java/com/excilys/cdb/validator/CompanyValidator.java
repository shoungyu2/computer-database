package com.excilys.cdb.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.model.Company;

@Component
public class CompanyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Company.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
		Company comp= (Company) target;
		if(comp.getId()<1) {
			errors.rejectValue("id", "wrong.id");
		}
		
	}

}
