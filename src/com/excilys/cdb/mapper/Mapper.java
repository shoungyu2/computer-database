package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanieService;


public class Mapper {

	private static final DateTimeFormatter DTF= DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final CompanieService companieService;
	
	public Mapper() {
		
		companieService=new CompanieService();
		
	}
	
	
	public LocalDateTime stringToDate(String str) {
		
		return LocalDate.parse(str, DTF).atStartOfDay();
		
	}
	
	public Companie stringToCompanie(String id) throws NotFoundException {
		
		return companieService.showDetailCompanieService(id);
		
	}
	
	public int stringToID(String id) {
		
		return Integer.parseInt(id);
				
	}
	
	public Computer stringToComputer(List<String> infoComp) throws NotFoundException {
		
		int idComputer=stringToID(infoComp.get(0));
		String name= infoComp.get(1);
		LocalDateTime introDate=stringToDate(infoComp.get(2));
		LocalDateTime discDate=stringToDate(infoComp.get(3));
		Companie comp=stringToCompanie(infoComp.get(4));
		return new Computer.ComputerBuilder(name, idComputer)
				.setIntroductDate(introDate)
				.setDiscontinueDate(discDate)
				.setEntreprise(comp)
				.build();
	
	}
	
}
