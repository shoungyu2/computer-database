package com.excilys.cdb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class VerificationService {
	
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;

	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public void verifDate(LocalDateTime introLDT, LocalDateTime discLDT, List<Problems> listProbs) {
		
		if((introLDT!=null && discLDT!=null) && (introLDT.isAfter(discLDT))) {
			listProbs.add(Problems.createInvalidDatesProblem(introLDT.toString()+">"+discLDT.toString()));
		}
		
		if(introLDT!=null && (introLDT.getYear()>2100 || introLDT.getYear()<1970)) {
			listProbs.add(Problems.createDateOutOfBoundsProblem(introLDT.toString()));
		}
		
		if(discLDT!=null && (discLDT.getYear()>2100 || discLDT.getYear()<1970)) {
			listProbs.add(Problems.createDateOutOfBoundsProblem(discLDT.toString()));
		}
		
	}
	
	public void verifNameIsNotNull(String name,List<Problems> listProbs){
		
		if(name==null || name.equals("")) {
			listProbs.add(Problems.createNameIsNullProblem(name));
		}
		
	}
	
	public void verifIDComputerInBDD(int id) throws NotFoundException{
		
		Optional<Computer> oc= computerDAO.getComputerFromId(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID Not Found");
		}
		
	}
	
	public void verifIDCompanieInBDD(int id) throws NotFoundException{
		
		Optional<Company> oc= companyDAO.showDetailCompany(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("ID not found");
		}
		
	}
	
}
