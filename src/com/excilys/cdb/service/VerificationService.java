package com.excilys.cdb.service;

import java.time.LocalDateTime;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;

public class VerificationService {

	public static void verifDate(LocalDateTime introLDT, LocalDateTime discLDT) throws DateInvalideException{
		
		if((introLDT!=null && discLDT!=null) && (introLDT.isAfter(discLDT))) {
			throw new DateInvalideException("La date d'introduction doit être antérieure à la date de retrait");
		}
		
	}
	
	public static void verifNameIsNotNull(String name) throws NameIsNullException{
		
		if(name!=null) {
			throw new NameIsNullException("Name cannot be null");
		}
		
	}
	
}
