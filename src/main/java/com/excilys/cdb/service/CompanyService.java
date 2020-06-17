package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

public class CompanyService {
	
	private VerificationService verifServ;
	private CompanyDAO compDAO;
	private Mapper map;
	
	public void setVerifServ(VerificationService verifServ) {
		this.verifServ = verifServ;
	}

	public void setCompDAO(CompanyDAO compDAO) {
		this.compDAO = compDAO;
	}
	
	public void setMap(Mapper map) {
		this.map=map;
	}

	public List<Company> listCompanyService(){
		
		return compDAO.listCompany();
		
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
	
	private Optional<Company> getCompanyFromId(String id, int idComp) throws InvalidEntryException{
		
		if(idComp!=-1) {
			try {
				verifServ.verifIDCompanieInBDD(idComp);
				return compDAO.showDetailCompany(idComp);
			} catch (NotFoundException nfe) {
				List<Problems> listProb=new ArrayList<Problems>();
				listProb.add(Problems.createIDNotFoundProblem(id));
				throw new InvalidEntryException(listProb);
			}
		}
		return Optional.empty();
		
	}
	
	public Optional<Company> showDetailCompanyService(String id) throws InvalidEntryException{
		
		int idComp=getIDFromString(id);
		return getCompanyFromId(id, idComp);
		
	}
	
}
