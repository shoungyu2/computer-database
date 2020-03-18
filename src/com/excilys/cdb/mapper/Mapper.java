package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.service.VerificationService;


public class Mapper {

	private static final DateTimeFormatter DTF= DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private CompanieDAO companieDAO;
	private List<Problems> parseProb=new ArrayList<Problems>();
	private VerificationService verifService;
	
	public List<Problems> getParseProb() {
		return this.parseProb;
	}
	
	public void setParseProb(List<Problems> parseProb) {
		this.parseProb=parseProb;
	}
	
	public void setVerifService(VerificationService verifService) {
		this.verifService=verifService;
	}
	
	public void setCompanieDAO(CompanieDAO companieDAO) {
		this.companieDAO=companieDAO;
	}
	
	public int stringToID(String id) {
		
		int res=0;
		try {
			res = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
			parseProb.add(Problems.createNotAnIDProblem(id));
		}
		return res;
		
	}
	
	public LocalDateTime stringToDate(String str) {
		
		return LocalDate.parse(str, DTF).atStartOfDay();
		
	}
	
	public Optional<Companie> stringToCompanie(String id) {
		
		int idComp=stringToID(id);
		try {
			verifService.verifIDCompanieInBDD(idComp);
		} catch(NotFoundException nfe) {
			parseProb.add(Problems.createIDNotFoundProblem(id));
		}
		return companieDAO.showDetailCompanie(idComp);
		
	}	
	
	public Computer stringToComputer(List<String> infoComp) {
		
		
		int idComputer=stringToID(infoComp.get(0));
		if(idComputer!=0) {
			try {
				verifService.verifIDComputerInBDD(idComputer);
			} catch (NotFoundException nfe) {
				parseProb.add(Problems.createIDNotFoundProblem(infoComp.get(0)));
			}
		}
		
		String name= infoComp.get(1);
		try {
			verifService.verifNameIsNotNull(name);
		} catch (NameIsNullException nine) {
			parseProb.add(Problems.createNameIsNullProblem(name));
		}
		
		LocalDateTime introDate=null;
		try {
			introDate=stringToDate(infoComp.get(2));
		} catch (DateTimeParseException dtpe) {
			parseProb.add(Problems.createNotADateProblem(infoComp.get(2)));
		}
		
		LocalDateTime discDate=null;
		try {
			discDate=stringToDate(infoComp.get(3));
		} catch(DateTimeParseException dtpe) {
			parseProb.add(Problems.createNotADateProblem(infoComp.get(3)));
		}
		
		try {
			verifService.verifDate(introDate, discDate);
		} catch (DateInvalideException die) {
			parseProb.add(Problems.createInvalidDatesProblem(infoComp.get(2)+" and "+infoComp.get(3)));
		}
		
		Optional<Companie> oc=stringToCompanie(infoComp.get(4));
		Companie comp=oc.isEmpty()?
				null:oc.get();
		return new Computer.ComputerBuilder(name, idComputer)
				.setIntroductDate(introDate)
				.setDiscontinueDate(discDate)
				.setEntreprise(comp)
				.build();
	
	}
	
}
