package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dto.ComputerDTO;
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
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(ComputerDAO compDAO) {
		this.compDAO = compDAO;
	}

	public void setMap(Mapper map) {
		this.map = map;
	}

	public int getNbrComputerService() {
		return compDAO.getNbrComputer();
	}
	
	public List<Computer> listComputerService(Page page){
		
		return compDAO.listComputer(page);		
	
	}
	
	public Optional<Computer> showDetailComputerService(String id) throws InvalidEntryException {
		
		int idComp=-1;
		try {
			idComp=map.stringToID(id);
		} catch(NumberFormatException nfe) {
			ArrayList<Problems> listProbs=new ArrayList<Problems>();
			listProbs.add(Problems.createNotAnIDProblem(id));
			throw new InvalidEntryException(listProbs);
		}
		if(idComp!=-1) {
			try {
				verifServ.verifIDComputerInBDD(idComp);
				return compDAO.showDetailComputer(idComp);
			} catch (NotFoundException nfe) {
				ArrayList<Problems> listProbs=new ArrayList<Problems>();
				listProbs.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(listProbs);
			}
		}
		return Optional.empty();
		
	}
	
	public void createComputerService(ComputerDTO infoComp) throws InvalidEntryException, ComputerIsNullException{
		
		Computer c;
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
	
	public void updateComputerService(ComputerDTO infoComp) throws InvalidEntryException, ComputerIsNullException {
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		verifServ.verifNameIsNotNull(c.getName(), listProb);
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate(), listProb);
		try {
			verifServ.verifIDComputerInBDD(c.getId());
		} catch (NotFoundException nfe) {
			listProb.add(Problems.createIDNotFoundProblem(infoComp.getId()));
		}
		if(listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		compDAO.updateComputer(c);
		
	}
	
	public void deleteComputerService(String id) throws InvalidEntryException{
		
		int idComp=0;
		try {
			idComp=map.stringToID(id);
		} catch(NumberFormatException nfe) {
			ArrayList<Problems> listProbs=new ArrayList<Problems>();
			listProbs.add(Problems.createNotAnIDProblem(id));
			throw new InvalidEntryException(listProbs);
		}
		try {
			verifServ.verifIDComputerInBDD(idComp);
		} catch(NotFoundException nfe) {
			ArrayList<Problems> listProbs=new ArrayList<Problems>();
			listProbs.add(Problems.createIDNotFoundProblem(id));
			throw new InvalidEntryException(listProbs);
		}
		compDAO.deleteComputer(idComp);
		
	}
	
	
	
}
