package com.excilys.cdb.dto.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {

	private final Validator companyDTOValidator;
	
	@Autowired
	public ComputerDTOValidator(Validator companyDTOValidator) {

		if(companyDTOValidator == null) {
			throw new IllegalArgumentException("The [CompanyDTOValidator] is required and can't be null");
		}
		if(!companyDTOValidator.supports(CompanyDTO.class)) {
			throw new IllegalArgumentException("The [CompanyDTOValidator] must support the validation of [CompanyDTO] instances");
		}
		this.companyDTOValidator=companyDTOValidator;
	
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.Builder.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ComputerDTO.Builder tg= (ComputerDTO.Builder) target;
		CompanyDTO cdto= tg.build().getCompanyDTO();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required");
		
		try {
			errors.pushNestedPath("companyDTO");
			if(cdto!=null) {
				ValidationUtils.invokeValidator(companyDTOValidator, cdto, errors);
			}
		} finally {
			errors.popNestedPath();
		}
		
	}

}
