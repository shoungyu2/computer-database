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
	
	public int getNbrComputerService(String search) {
		return compDAO.getNbrComputer(search);
	}
	
	private int getIDFromString(String id) throws InvalidEntryException {
		
		try {
			return map.stringToID(id);
		} catch (NumberFormatException nfe) {
			List<Problems> listProb=new ArrayList<Problems>();
			listProb.add(Problems.createNotAnIDProblem(id));
			throw new InvalidEntryException(listProb);
		}
		
	}
	
	private Optional<Computer> getComputerFromId(String id, int idComp) throws InvalidEntryException{
		
		if(idComp!=-1) {
			try {
				verifServ.verifIDComputerInBDD(idComp);
				return compDAO.getComputerFromId(idComp);
			} catch (NotFoundException nfe) {
				List<Problems> listProb=new ArrayList<Problems>();
				listProb.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(listProb);
			}
		}
		
		return Optional.empty();
		
	}
	
	public Optional<Computer> getComputerFromIdService(String id) throws InvalidEntryException {
		
		int idComp=getIDFromString(id);
		return getComputerFromId(id, idComp);
		
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
	
	private List<Problems> generateProblems(ComputerDTO infoComp, Computer c, List<Problems> listProb){
		
		listProb=map.getParseProb();
		verifServ.verifNameIsNotNull(c.getName(), listProb);
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate(), listProb);
		try {
			verifServ.verifIDComputerInBDD(c.getId());
		} catch (NotFoundException nfe) {
			listProb.add(Problems.createIDNotFoundProblem(infoComp.getId()));
		}
		return listProb;
		
	}
	
	public void updateComputerService(ComputerDTO infoComp) throws InvalidEntryException, ComputerIsNullException {
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=generateProblems(infoComp, c, map.getParseProb());
		if(listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		compDAO.updateComputer(c);
		
	}
	
	public void deleteComputerService(String id) throws InvalidEntryException{
		
		int idComp=getIDFromString(id);
		try {
			verifServ.verifIDComputerInBDD(idComp);
		} catch(NotFoundException nfe) {
			ArrayList<Problems> listProbs=new ArrayList<Problems>();
			listProbs.add(Problems.createIDNotFoundProblem(id));
			throw new InvalidEntryException(listProbs);
		}
		compDAO.deleteComputer(idComp);
		
	}
	
	public List<Computer> getComputersService(String search, String filter, String order, Page page){
		
		return compDAO.getComputers(search, filter, order, page);
		
	}
	
}
