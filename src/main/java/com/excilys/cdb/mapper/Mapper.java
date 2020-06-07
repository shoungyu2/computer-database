package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.CompanyIsNullException;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;


public class Mapper {

	private static final DateTimeFormatter DTF= DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private List<Problems> parseProb=new ArrayList<Problems>();
	
	public List<Problems> getParseProb() {
		return this.parseProb;
	}
	
	public void setParseProb(List<Problems> parseProb) {
		this.parseProb=parseProb;
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
		
		LocalDateTime ldt=null;
		try {
			ldt= str==null || str.contentEquals("")
					?null:LocalDate.parse(str, DTF).atStartOfDay();
		} catch (DateTimeParseException dtpe) {
			parseProb.add(Problems.createNotADateProblem(str));
		}
		return ldt;
	}
	
	public Optional<Companie> stringToCompanie(CompanyDTO cdto) throws CompanyIsNullException {
		
		if(cdto!=null) {
			if(!(cdto.getId()==null || cdto.getId().isEmpty())) {
				int idComp=stringToID(cdto.getId());
				Companie comp= new Companie(cdto.getName(), idComp);
				return Optional.of(comp);
			}
			else {
				return Optional.empty();
			}
		}
		else {
			throw new CompanyIsNullException();
		}
	}	
	
	public Computer stringToComputer(ComputerDTO infoComp) throws CompanyIsNullException, ComputerIsNullException {
		
		if(infoComp!=null) {
			int idComputer=stringToID(infoComp.getId());
			
			String name= infoComp.getName();
			
			LocalDateTime introDate=stringToDate(infoComp.getIntroduced());
			
			LocalDateTime discDate=stringToDate(infoComp.getDiscontinued());
			
			Optional<Companie> oc=stringToCompanie(infoComp.getCompanyDTO());
			Companie comp=oc.isEmpty()?
					null:oc.get();
			return new Computer.ComputerBuilder(name, idComputer)
					.setIntroductDate(introDate)
					.setDiscontinueDate(discDate)
					.setEntreprise(comp)
					.build();
		}
		else {
			throw new ComputerIsNullException();
		}
	}
	
}
