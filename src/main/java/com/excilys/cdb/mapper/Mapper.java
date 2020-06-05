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
import com.excilys.cdb.service.VerificationService;


public class Mapper {

	private static final DateTimeFormatter DTF= DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
		
		if(!(id==null || id.isEmpty())) {
			int idComp=stringToID(id);
			try {
				Optional<Companie> opComp= verifService.verifIDCompanieInBDD(idComp);
				return opComp;
			} catch(NotFoundException nfe) {
				parseProb.add(Problems.createIDNotFoundProblem(id));
				return Optional.empty();
			}
			
		}
		else {
			
			return Optional.empty();
			
		}
	}	
	
	public Computer stringToComputer(List<String> infoComp) {
		
		
		int idComputer=stringToID(infoComp.get(0));
		if(idComputer!=-1) {
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
			introDate=
					infoComp.get(2)==null || infoComp.get(2).isEmpty() 
					? null : stringToDate(infoComp.get(2));
		} catch (DateTimeParseException dtpe) {
			parseProb.add(Problems.createNotADateProblem(infoComp.get(2)));
		}
		
		LocalDateTime discDate=null;
		try {
			discDate=
					infoComp.get(3)==null || infoComp.get(3).isEmpty() 
					? null : stringToDate(infoComp.get(3));
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
