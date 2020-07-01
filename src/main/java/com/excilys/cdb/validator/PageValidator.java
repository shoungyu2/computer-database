package com.excilys.cdb.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.model.Page;

public class PageValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Page.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Page page= (Page) target;
		if(page.getNumPage()<1) {
			errors.rejectValue("numPage", "negative.value");
		}
		if(page.getOffset()<0) {
			errors.rejectValue("offset", "negative.value");
		}
		if(Page.getNbrElements()<1) {
			errors.rejectValue("nbrElements", "negative.value");
		}
		if(Page.getNbrPages()<1) {
			errors.rejectValue("nbrPages", "negative.value");
		}
	}

}
