package com.excilys.cdb.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;

/**
 * Classe implémentant une CLI pour les différentes opérations de la BDD
 * @author masterchief
 */
public class CommandeLineInterface {

	private final static Scanner SC=new Scanner(System.in);
	
	public static void listComputerCLI() {
		
		List<Computer> listComp=ComputerDAO.listComputer();
		
		System.out.println("Nombre de PC dans la BDD: "+listComp.size());
		
		System.out.println("Voulez vous les afficher(o/n)?");
		String rep=SC.nextLine();
		if(rep.equals("o")) {
			for(Computer c: listComp) {
				System.out.println(c);
				System.out.println();
			}
		}
		
	}
	
	public static void showComputerCLI() {
		
		System.out.println("Computer ID:");
		String str=SC.nextLine();
		int id=Integer.parseInt(str);
		
		new ComputerDAO();
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		System.out.println("Voici les informations demandées: ");
		System.out.println(oc.get());
	
	}
	
	public static void updateComputerCLI() {
		
		System.out.println("Computer ID(mandatory):");
		int id=Integer.parseInt(SC.nextLine());
		
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		
		System.out.println("Computer introduced date(optional):");
		LocalDateTime introLDT=null;
		try {
			String str=SC.nextLine();
			introLDT=LocalDateTime.parse(str);
		} catch (NoSuchElementException nsee) {}
		
		System.out.println("Computer discontinued(optional):");
		LocalDateTime discLDT=null;
		try {
			String str=SC.nextLine();
			discLDT=LocalDateTime.parse(str);
		} catch(NoSuchElementException nsee) {}
		
		System.out.println("Company ID(optional):");
		Companie c=null;
		try {
			
			String str=SC.nextLine();
			int idComp=Integer.parseInt(str);
			c=CompanieDAO.showDetailCompanie(idComp).get();
		
		} catch(NoSuchElementException nsee) {}
		
		Computer computer=new Computer.ComputerBuilder(name, id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(c)
				.build();
		
		ComputerDAO.updateComputer(computer);
	}
}
