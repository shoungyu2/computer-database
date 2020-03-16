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
	
	public static LocalDateTime stringToDate(String str) {
		
		return LocalDate.parse(str, DTF).atStartOfDay();
		
	}
	
	public static Companie stringToCompanie(String id) throws NotFoundException {
		
		int idComp=Integer.parseInt(id);
		return CompanieService.showDetailCompanie(idComp);
		
	}
	
	public static int stringToID(String id) {
		
		return Integer.parseInt(id);
				
	}
	
	public static Computer stringToComputer(List<String> infoComp) throws NotFoundException {
		
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
