package com.excilys.cdb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class VerificationService {
	
	private ComputerDAO computerDAO;
	private CompanieDAO companieDAO;

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public void setCompanieDAO(CompanieDAO companieDAO) {
		this.companieDAO = companieDAO;
	}

	public void verifDate(LocalDateTime introLDT, LocalDateTime discLDT) throws DateInvalideException{
		
		if((introLDT!=null && discLDT!=null) && (introLDT.isAfter(discLDT))) {
			throw new DateInvalideException("La date d'introduction doit être antérieure à la date de retrait");
		}
		
	}
	
	public void verifNameIsNotNull(String name) throws NameIsNullException{
		
		if(name==null) {
			throw new NameIsNullException("Name cannot be null");
		}
		
	}
	
	public void verifIDComputerInBDD(int id) throws NotFoundException{
		
		Optional<Computer> oc= computerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID Not Found");
		}
		
	}
	
	public  void verifIDCompanieInBDD(int id) throws NotFoundException{
		
		Optional<Companie> oc= companieDAO.showDetailCompanie(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID not found");
		}
		
	}
	
}
