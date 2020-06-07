package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.CompanyIsNullException;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {

	private VerificationService verifServ;
	private ComputerDAO compDAO;
	private Mapper map;
	private List<Problems> compProblems=new ArrayList<Problems>();
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(ComputerDAO compDAO) {
		this.compDAO = compDAO;
	}

	public void setMap(Mapper map) {
		this.map = map;
	}

	public List<Computer> listComputerService(Page page){
		
		return compDAO.listComputer(page);		
	
	}
	
	public Optional<Computer> showDetailComputerService(String id) throws InvalidEntryException {
		
		int idComp=0;
		try {
			idComp=map.stringToID(id);
		} catch(NumberFormatException nfe) {
			compProblems.add(Problems.createNotAnIDProblem(id));
			throw new InvalidEntryException(compProblems);
		}
		if(idComp!=0) {
			try {
				verifServ.verifIDComputerInBDD(idComp);
				return compDAO.showDetailComputer(idComp);
			} catch (NotFoundException nfe) {
				compProblems.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(compProblems);
			}
		}
		return Optional.empty();
		
	}
	
	public void createComputerService(ComputerDTO infoComp) throws InvalidEntryException, CompanyIsNullException, ComputerIsNullException{
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		verifServ.verifNameIsNotNull(c.getName(), listProb);
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate(), listProb);
		if (listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		compDAO.createComputer(c);
		
	}
	
	public void updateComputerService(ComputerDTO infoComp) throws InvalidEntryException, CompanyIsNullException, ComputerIsNullException {
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		verifServ.verifNameIsNotNull(c.getName(), listProb);
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate(), listProb);
		try {
			verifServ.verifIDComputerInBDD(c.getID());
			compDAO.updateComputer(c);
		} catch (NotFoundException nfe) {
			listProb.add(Problems.createIDNotFoundProblem(infoComp.getId()));
		}
		if(listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		
	}
	
	public void deleteComputerService(String id) throws InvalidEntryException{
		
		int idComp=0;
		try {
			idComp=map.stringToID(id);
		} catch(NumberFormatException nfe) {
			compProblems.add(Problems.createNotAnIDProblem(id));
			throw new InvalidEntryException(compProblems);
		}
		try {
			verifServ.verifIDComputerInBDD(idComp);
		} catch(NotFoundException nfe) {
			compProblems.add(Problems.createIDNotFoundProblem(id));
			throw new InvalidEntryException(compProblems);
		}
		compDAO.deleteComputer(idComp);
		
	}
	
	
	
}
