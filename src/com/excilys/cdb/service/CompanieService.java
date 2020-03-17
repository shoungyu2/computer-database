package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.persistence.CompanieDAO;

public class CompanieService {
	
	private VerificationService verifServ;
	private CompanieDAO compDAO;
	private Mapper map;
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(CompanieDAO compDAO) {
		this.compDAO = compDAO;
	}
	
	public void setMap(Mapper map) {
		this.map=map;
	}

	public List<Companie> listCompanieService(){
		
		return compDAO.listCompanie();
		
	}
	
	public Companie showDetailCompanieService(String id) throws NotFoundException{
		
		int idComp=map.stringToID(id);
		verifServ.verifIDCompanieInBDD(idComp);
		return compDAO.showDetailCompanie(idComp).get();
		
	}
	
}
