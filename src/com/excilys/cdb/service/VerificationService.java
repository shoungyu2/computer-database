package com.excilys.cdb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

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
	
	public static void verifIDInBDD(int id) throws NotFoundException{
		
		Optional<Computer> oc= ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID Not Found");
		}
		
	}
	
}
