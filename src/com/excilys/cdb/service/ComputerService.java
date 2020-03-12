package com.excilys.cdb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {

	public static List<Computer> listComputerService(){
		
		return ComputerDAO.listComputer();		
	
	}
	
	public static Optional<Computer> showDetailComputerService(int id) {
		
		return ComputerDAO.showDetailComputer(id);
		
	}
	
	public static void createComputerService(Computer c) throws DateInvalideException{
		
		VerificationService.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		ComputerDAO.createComputer(c);
		
	}
	
	public static void updateComputerService(Computer c) {
		
		ComputerDAO.updateComputer(c);
		
	}
	
	public static void deleteComputer(int id) {
		
		ComputerDAO.deleteComputer(id);
		
	}
	
	
	
}
