package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {

	public static List<Computer> listComputerService(){
		
		return ComputerDAO.listComputer();		
	
	}
	
	public static Computer showDetailComputerService(int id) throws NotFoundException{
		
		VerificationService.verifIDComputerInBDD(id);
		return ComputerDAO.showDetailComputer(id).get();
		
	}
	
	public static void createComputerService(List<String> infoComp) throws NotFoundException, DateInvalideException, NameIsNullException{
		
		Computer c=Mapper.stringToComputer(infoComp);
		VerificationService.verifNameIsNotNull(c.getName());
		VerificationService.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		VerificationService.verifIDCompanieInBDD(c.getEntreprise().getId());
		ComputerDAO.createComputer(c);
		
	}
	
	public static void updateComputerService(List<String> infoComp) throws DateInvalideException, NameIsNullException, NotFoundException{
		
		Computer c=Mapper.stringToComputer(infoComp);
		VerificationService.verifIDComputerInBDD(c.getID());
		VerificationService.verifNameIsNotNull(c.getName());
		VerificationService.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		VerificationService.verifIDCompanieInBDD(c.getEntreprise().getId());
		ComputerDAO.updateComputer(c);
		
	}
	
	public static void deleteComputer(int id) throws NotFoundException{
		
		VerificationService.verifIDComputerInBDD(id);
		ComputerDAO.deleteComputer(id);
		
	}
	
	
	
}
