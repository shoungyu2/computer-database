package com.excilys.cdb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class VerificationService {
	
	private ComputerDAO computerDAO;
	private CompanieDAO companieDAO;

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public void setCompanieDAO(CompanieDAO companieDAO) {
		this.companieDAO = companieDAO;
	}

	public void verifDate(LocalDateTime introLDT, LocalDateTime discLDT, List<Problems> listProbs) {
		
		if((introLDT!=null && discLDT!=null) && (introLDT.isAfter(discLDT))) {
			listProbs.add(Problems.createInvalidDatesProblem(introLDT.toString()+">"+discLDT.toString()));
		}
		
	}
	
	public void verifNameIsNotNull(String name,List<Problems> listProbs){
		
		if(name==null || name.equals("")) {
			listProbs.add(Problems.createNameIsNullProblem(name));
		}
		
	}
	
	public void verifIDComputerInBDD(int id) throws NotFoundException{
		
		Optional<Computer> oc= computerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID Not Found");
		}
		
	}
	
	public void verifIDCompanieInBDD(int id) throws NotFoundException{
		
		Optional<Companie> oc= companieDAO.showDetailCompanie(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID not found");
		}
		
	}
	
}
