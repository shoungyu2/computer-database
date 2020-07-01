package com.excilys.cdb.validator;

import java.time.LocalDateTime;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerValidator implements Validator{

	private final Validator companyValidator;
	
	public ComputerValidator(Validator companyValidator) {
		
		if(companyValidator == null) {
			throw new IllegalArgumentException("The [CompanyValidator] is required and can't be null");
		}
		if(!companyValidator.supports(Company.class)) {
			throw new IllegalArgumentException("The [CompanyValidator] must support the validation of [Company] instances");
		}
		this.companyValidator=companyValidator;
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Computer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
		
		Computer comp = (Computer) target;
		if(comp.getId()<-1) {
			errors.rejectValue("id", "wrong.id");
		}
		LocalDateTime intro= comp.getIntroductDate();
		LocalDateTime disc= comp.getDiscontinueDate();
		if(intro.isAfter(disc)) {
			errors.rejectValue("", "intro.must.be.before.disc");
		}
		try {
			errors.pushNestedPath("company");
			ValidationUtils.invokeValidator(companyValidator, comp.getCompany(), errors);
		} finally {
			errors.popNestedPath();
		}
		
	}

}
