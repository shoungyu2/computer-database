package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {

	private VerificationService verifServ;
	private ComputerDAO compDAO;
	private Mapper map;
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(ComputerDAO compDAO) {
		this.compDAO = compDAO;
	}

	public void setMap(Mapper map) {
		this.map = map;
	}

	public List<Computer> listComputerService(){
		
		return compDAO.listComputer();		
	
	}
	
	public Computer showDetailComputerService(String id) throws NotFoundException{
		
		
		int idComp=map.stringToID(id);
		verifServ.verifIDComputerInBDD(idComp);
		return compDAO.showDetailComputer(idComp).get();
		
	}
	
	public void createComputerService(List<String> infoComp) throws NotFoundException, DateInvalideException, NameIsNullException{
		
		Computer c=map.stringToComputer(infoComp);
		verifServ.verifNameIsNotNull(c.getName());
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		verifServ.verifIDCompanieInBDD(c.getEntreprise().getId());
		compDAO.createComputer(c);
		
	}
	
	public void updateComputerService(List<String> infoComp) throws DateInvalideException, NameIsNullException, NotFoundException{
		
		Computer c=map.stringToComputer(infoComp);
		verifServ.verifIDComputerInBDD(c.getID());
		verifServ.verifNameIsNotNull(c.getName());
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate());
		verifServ.verifIDCompanieInBDD(c.getEntreprise().getId());
		compDAO.updateComputer(c);
		
	}
	
	public void deleteComputerService(String id) throws NotFoundException{
		
		int idComp=map.stringToID(id);
		verifServ.verifIDComputerInBDD(idComp);
		compDAO.deleteComputer(idComp);
		
	}
	
	
	
}
