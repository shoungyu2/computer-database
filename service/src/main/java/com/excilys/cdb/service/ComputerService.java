package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAO;

@Service
public class ComputerService {

	@Autowired
	private VerificationService verifServ;
	@Autowired
	private ComputerDAO compDAO;
	@Autowired
	private Mapper map;
	
	public List<Computer> getComputersService(String search, String filter, String order, Page page){
		
		if(search==null) {
			search="";
		}
		search=search.replace("%", "\\search");
		
		return compDAO.getComputers(search, filter, order, page);
		
	}

	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(ComputerDAO compDAO) {
		this.compDAO = compDAO;
	}

	public void setMap(Mapper map) {
		this.map = map;
	}
	
	public long getNbrComputerService(String search) {
		
		if(search==null) {
			search="";
		}
		search=search.replace("%", "\\%");
		
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
	
	public boolean createComputerService(ComputerDTO infoComp) throws InvalidEntryException, ComputerIsNullException{
		
		Computer c;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=map.getParseProb();
		verifServ.verifNameIsNotNull(c.getName(), listProb);
		verifServ.verifDate(c.getIntroductDate(), c.getDiscontinueDate(), listProb);
		if (listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		return compDAO.createComputer(c);
		
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
	
	public boolean updateComputerService(ComputerDTO infoComp) throws InvalidEntryException, ComputerIsNullException {
		
		Computer c=null;
		c=map.stringToComputer(infoComp);
		List<Problems> listProb=generateProblems(infoComp, c, map.getParseProb());
		if(listProb.size()!=0) {
			map.setParseProb(new ArrayList<Problems>());
			throw new InvalidEntryException(listProb);
		}
		
		return compDAO.updateComputer(c);
		
	}
	
	public boolean deleteComputerService(String id) throws InvalidEntryException{
		
		int idComp=getIDFromString(id);
		try {
			verifServ.verifIDComputerInBDD(idComp);
		} catch(NotFoundException nfe) {
			ArrayList<Problems> listProbs=new ArrayList<Problems>();
			listProbs.add(Problems.createIDNotFoundProblem(id));
			throw new InvalidEntryException(listProbs);
		}
		return compDAO.deleteComputer(idComp);
		
	}

}
