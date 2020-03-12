package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {

	public static List<Computer> listComputerService(){
		
		return ComputerDAO.listComputer();		
	
	}
	
	public static Computer showDetailComputerService(int id) throws NotFoundException{
		
		VerificationService.verifIDInBDD(id);
		return ComputerDAO.showDetailComputer(id).get();
		
	}
	
	public static void createComputerService(Computer c) throws DateInvalideException, NameIsNullException{
		
		VerificationService.verifNameIsNotNull(c.getName());
		VerificationService.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		ComputerDAO.createComputer(c);
		
	}
	
	public static void updateComputerService(Computer c) throws DateInvalideException, NameIsNullException, NotFoundException{
		
		VerificationService.verifIDInBDD(c.getID());
		VerificationService.verifNameIsNotNull(c.getName());
		VerificationService.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		ComputerDAO.updateComputer(c);
		
	}
	
	public static void deleteComputer(int id) throws NotFoundException{
		
		VerificationService.verifIDInBDD(id);
		ComputerDAO.deleteComputer(id);
		
	}
	
	
	
}
