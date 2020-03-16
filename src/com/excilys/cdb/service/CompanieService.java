package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.persistence.CompanieDAO;

public class CompanieService {

	public static List<Companie> listCompanieService(){
		
		return CompanieDAO.listCompanie();
		
	}
	
	public static Companie showDetailCompanie(int id) throws NotFoundException{
		
		VerificationService.verifIDCompanieInBDD(id);
		return CompanieDAO.showDetailCompanie(id).get();
		
	}
	
}
