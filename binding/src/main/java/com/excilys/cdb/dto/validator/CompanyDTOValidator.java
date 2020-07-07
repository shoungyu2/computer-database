package com.excilys.cdb.dto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.CompanyDTO;

@Component
public class CompanyDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CompanyDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required");
		
	}

}
