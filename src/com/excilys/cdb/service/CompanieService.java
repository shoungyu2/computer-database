package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanieDAO;

public class CompanieService {
	
	private VerificationService verifServ;
	private CompanieDAO compDAO;
	private Mapper map;
	private List<Problems> listProb=new ArrayList<>();
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(CompanieDAO compDAO) {
		this.compDAO = compDAO;
	}
	
	public void setMap(Mapper map) {
		this.map=map;
	}

	public List<Companie> listCompanieService(Page page){
		
		return compDAO.listCompanie(page);
		
	}
	
	public Optional<Companie> showDetailCompanieService(String id) throws InvalidEntryException{
		
		int idComp=0;
		try {
			idComp=map.stringToID(id);
		} catch (NumberFormatException nfe) {
			listProb.add(Problems.createNotAnIDProblem(id));
		}
		if(idComp!=0) {
			try {
				return verifServ.verifIDCompanieInBDD(idComp);
			} catch (NotFoundException nfe) {
				listProb.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(listProb);
			}
		}
		return Optional.empty();
		
	}
	
}
