package com.excilys.cdb.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.exception.NotFoundException;
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
	
	/**
	 * La CLI pour afficher les ordinateur de la BDD
	 */
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
	
	public static void listCompanieCLI() {
		
		List<Companie> listComp=CompanieDAO.listCompanie();
		
		System.out.println("Nombre de companies dans la BDD: "+listComp.size());
		
		System.out.println("Voulez vous les afficher(o/n)?");
		String rep=SC.nextLine();
		if(rep.equals("o")) {
			for (Companie c: listComp) {
				System.out.println(c);
				System.out.println();
			}
		}
		
	}
	
	/**
	 * La CLI pour chercher un ordinateur dans la BDD
	 * @throws NotFoundException si l'ID fournit n'est pas dans la base
	 */
	public static void showComputerCLI() throws NotFoundException{
				
		System.out.println("Computer ID:");
		String str=SC.nextLine();
		int id=Integer.parseInt(str);
		
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		
		System.out.println("Voici les informations demandées: ");
		System.out.println(oc.get());
	
	}
	
	/**
	 * La CLI pour mettre à jour un ordinateur dans la BDD
	 * @throws NotFoundException si l'ID fournit n'est pas dans la base
	 */
	public static void updateComputerCLI() throws NotFoundException {
		
		System.out.println("Computer ID(mandatory):");
		int id=Integer.parseInt(SC.nextLine());
		
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		
		System.out.println("Computer introduced date(optional):");
		LocalDateTime introLDT=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				introLDT=LocalDateTime.parse(str);
			}
		} catch (DateTimeParseException nsee) {
			System.out.println("Date invlaide, valeur mise à null");
		}
		
		System.out.println("Computer discontinued(optional):");
		LocalDateTime discLDT=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				discLDT=LocalDateTime.parse(str);
			}
		} catch(DateTimeParseException nsee) {}
		
		System.out.println("Company ID(optional):");
		Companie c=null;
		try {
			
			String str=SC.nextLine();
			int idComp=Integer.parseInt(str);
			Optional<Companie> optComp=CompanieDAO.showDetailCompanie(idComp);
			if(optComp.isEmpty()) {
				throw new NotFoundException("Company Not Found");
			}
			c=optComp.get();
		} catch(NoSuchElementException nsee) {}
		
		Computer computer=new Computer.ComputerBuilder(name, id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(c)
				.build();
		
		System.out.println("Données mise à jour: ");
		System.out.println(computer);
		ComputerDAO.updateComputer(computer);
	}
	
	/**
	 * La CLI pour la création d'un ordinateur
	 */
	public static void createComputerCLI() {
		
		System.out.println("Computer ID(mandatory):");
		int id=Integer.parseInt(SC.nextLine());
		
		System.out.println("Computer name(mandatory):");
		String name=SC.nextLine();
		
		System.out.println("Computer introduced date(optional):");
		LocalDateTime introLDT=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				introLDT=LocalDateTime.parse(str);
			}
		} catch (DateTimeParseException nsee) {
			System.out.println("Date invalide, valeur mise à null");
		}
		
		System.out.println("Computer discontinued date(optional):");
		LocalDateTime discLDT=null;
		try {
			String str=SC.nextLine();
			if(!str.equals("")) {
				discLDT=LocalDateTime.parse(str);
			}
		} catch(DateTimeParseException nsee) {
			System.out.println("Date invalide, valeur mise à null");
		}
		
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
		
		ComputerDAO.createComputer(computer);
		System.out.println("Ordinateur crée: ");
		System.out.println(computer);
	}
	
	/**
	 * La CLI pour la destruction d'un ordinateur
	 * @throws NotFoundException si l'ID fournit n'est pas dans la base
	 */
	public static void deleteComputerCLI() throws NotFoundException{
		
		System.out.println("Computer ID:");
		int id=Integer.parseInt(SC.nextLine());
		
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		
		ComputerDAO.deleteComputer(id);
	}
	
	public static void afficheMenu() {
		System.out.println("Choisissez l'une des options suivantes");
		System.out.println();
		System.out.println("1.Lister les ordinateurs");
		System.out.println("2.Lister les companies");
		System.out.println("3.Afficher les détails d'un ordinateurs");
		System.out.println("4.Créer un ordinateur");
		System.out.println("5.Mettre à jour un ordinateur");
		System.out.println("6.Supprimer un ordinateur");
		System.out.println("7.Quitter");
	}
	
	public static boolean revenirMenu(boolean ok) {
		
		System.out.println("Revenir au menu ?");
		System.out.println();
		System.out.println("1. Oui");
		System.out.println("Autre entrée: Non et quitter");
		if(!(SC.nextLine().equals("1"))) {
			System.out.println("Au revoir");
			ok=true;
		}
		
		return ok;
		
	}
	
	public static void faitesVotreChoix() {
		
		boolean ok=false;
		
		while(!ok) {
			
			afficheMenu();
			
			try {
				int choix=Integer.parseInt(SC.nextLine());
				switch(choix) {
				
				case 1:
					listComputerCLI();
					ok=revenirMenu(ok);
					break;
				
				case 2:
					listCompanieCLI();
					ok=revenirMenu(ok);
					break;
					
				case 3:
					showComputerCLI();
					ok=revenirMenu(ok);
					break;
					
				case 4:
					createComputerCLI();
					ok=revenirMenu(ok);
					break;
					
				case 5:
					updateComputerCLI();
					ok=revenirMenu(ok);
					break;
				
				case 6:
					deleteComputerCLI();
					ok=revenirMenu(ok);
					break;
					
				default:
					System.out.println("Au revoir");
					ok=true;
					
				}
			}
			catch(NotFoundException nfe) {
				System.err.println(nfe.getMessage());
			}
			catch(NumberFormatException nfe) {
				System.err.println("Veuillez choisir un nombre entre 1 et 7");
			}
		}
	}
}
