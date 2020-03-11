package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

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
	
	protected static int choixID() {

		while(true) {
			try {
				int id=Integer.parseInt(SC.nextLine());
				if(id==0) {
					throw new NumberFormatException();
				}
				return id;
			} catch(NumberFormatException nfe) {
				System.out.println("ID invalide, veuillez choisir un entier > 0");
			}
		}
		
	}
	
	private static LocalDateTime choixDate() {
		
		LocalDateTime date=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				date=stringToDate(str);
			}
		} catch (DateTimeParseException nsee) {
			System.out.println("Date invalide, valeur mise à null");
		}
		
		return date;
		
	}
	
	private static Companie choixCompanie() throws NotFoundException{
		
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
			}
		} catch(NumberFormatException nsee) {
			System.out.println("ID invalide, valeur de la companie mise à null");
		}
		return c;
		
	}
	
	public static Computer choixComputer() throws NotFoundException{
		
		System.out.println("Computer ID(mandatory):");
		int id=choixID();
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		System.out.println("Computer introduced date(optional):");
		LocalDateTime introLDT=choixDate();
		System.out.println("Computer discontinued(optional):");
		LocalDateTime discLDT=choixDate();
		System.out.println("Company ID(optional):");
		Companie c=ReaderCLI.choixCompanie();
		Computer computer=new Computer.ComputerBuilder(name, id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(c)
				.build();
		return computer;
		
	}

}
