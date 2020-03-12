package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class ReaderCLI {

	private final static Scanner SC=new Scanner(System.in);
	private final static DateTimeFormatter DTF=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static LocalDateTime stringToDate(String str) {
		
		LocalDateTime ldt=LocalDate.parse(str, DTF).atStartOfDay();
		return ldt;
		
	}
	
	protected static int choixID() throws NumberFormatException {

		while(true) {
			try {
				int id=Integer.parseInt(SC.nextLine());
				if(id==0) {
					throw new NumberFormatException();
				}
				return id;
			} catch(NumberFormatException nfe) {
				throw new NumberFormatException("Veuillez choisir un entier > 0");
			}
		}
		
	}
	
	private static Optional<LocalDateTime> choixDate() throws DateTimeParseException{
		
		LocalDateTime date=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				date=stringToDate(str);
				return Optional.of(date);
			}
			return Optional.empty();
		} catch (DateTimeParseException dtpe) {
			throw dtpe;
		}
		
	}
	
	private static Optional<Companie> choixCompanie() throws NotFoundException, NumberFormatException{

		Companie c=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				int idComp=Integer.parseInt(str);
				Optional<Companie> optComp=CompanieDAO.showDetailCompanie(idComp);
				if(optComp.isEmpty()) {
					throw new NotFoundException("Company Not Found");
				}
				c=optComp.get();
				return Optional.of(c);
			}
			return Optional.empty();
		} catch(NumberFormatException nsee) {
			throw new NumberFormatException("ID invalide");
		}
		
	}
	
	private static int checkChoixID(List<Exception> listExp) {
		
		int id=0;
		try {
			id=choixID();
			Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
			if(oc.isEmpty()) {
				listExp.add(new NotFoundException("Computer Not Found"));
			}
		} catch (NumberFormatException nfe) {
			listExp.add(nfe);
		}
		
		return id;
		
	}
	
	private static Optional<LocalDateTime> checkChoixDate(List<Exception> listExp) {
		
		try {
			return choixDate();
		} catch (DateTimeParseException dtpe) {
			listExp.add(new DateTimeParseException("Date invalide", dtpe.getParsedString(), dtpe.getErrorIndex()));
		}
		return Optional.empty();
		
	}
	
	private static Optional<Companie> checkChoixCompanie(List<Exception> listExp) {
		
		try {
			return choixCompanie();
		} catch(NotFoundException nfe) {
			listExp.add(nfe);
		}
		catch(NumberFormatException nfe) {
			listExp.add(nfe);
		}
		return Optional.empty();
		
	}
	
	public static Computer choixComputerForUpdate() throws InvalidEntryException{
		
		List<Exception> listExp=new ArrayList<>();
		System.out.println("Computer ID(mandatory):");
		int id=checkChoixID(listExp);
		
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		
		System.out.println("Computer introduced date(optional):");
		Optional<LocalDateTime> optLDT1=checkChoixDate(listExp);
		LocalDateTime introLDT=optLDT1.isEmpty()?null:optLDT1.get();
		
		System.out.println("Computer discontinued(optional):");
		Optional<LocalDateTime> optLDT2=checkChoixDate(listExp);
		LocalDateTime discLDT=optLDT2.isEmpty()?null:optLDT2.get();
		  		
		System.out.println("Company ID(optional):");
		Optional<Companie> optComp=checkChoixCompanie(listExp);
		Companie c=optComp.isEmpty()?null:optComp.get();
		
		
		if(listExp.size()!=0) {
			throw new InvalidEntryException(listExp);
		}
		
		Computer computer=new Computer.ComputerBuilder(name, id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(c)
				.build();
		return computer;
		
	}
	
	public static Computer choixComputerForCreate() throws InvalidEntryException{
		
		List<Exception> listExp=new ArrayList<>();
		
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		
		System.out.println("Computer introduced date(optional):");
		Optional<LocalDateTime> optLDT1=checkChoixDate(listExp);
		LocalDateTime introLDT=optLDT1.isEmpty()?null:optLDT1.get();
		
		System.out.println("Computer discontinued(optional):");
		Optional<LocalDateTime> optLDT2=checkChoixDate(listExp);
		LocalDateTime discLDT=optLDT2.isEmpty()?null:optLDT2.get();
		
		System.out.println("Company ID(optional):");
		Optional<Companie> optComp=checkChoixCompanie(listExp);
		Companie c=optComp.isEmpty()?null:optComp.get();
		
		if(listExp.size()!=0) {
			throw new InvalidEntryException(listExp);
		}
		
		Computer computer=new Computer.ComputerBuilder(name, 1)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(c)
				.build();
		return computer;
		
	}

}
