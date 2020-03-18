package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
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

	public List<Computer> listComputerService(){
		
		return compDAO.listComputer();		
	
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
				return verifServ.verifIDComputerInBDD(idComp);
			} catch (NotFoundException nfe) {
				compProblems.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(compProblems);
			}
		}
		return Optional.empty();
		
	}
	
	public void createComputerService(List<String> infoComp) throws InvalidEntryException{
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		if (listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		compDAO.createComputer(c);
		
	}
	
	public void updateComputerService(List<String> infoComp) throws InvalidEntryException {
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		if(listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		compDAO.updateComputer(c);
		
	}
	
	public void deleteComputerService(String id) throws InvalidEntryException{
		
		int idComp=map.stringToID(id);
		try {
			verifServ.verifIDComputerInBDD(idComp);
		} catch(NotFoundException nfe) {
			compProblems.add(Problems.createIDNotFoundProblem(id));
			throw new InvalidEntryException(compProblems);
		}
		compDAO.deleteComputer(idComp);
		
	}
	
	
	
}
