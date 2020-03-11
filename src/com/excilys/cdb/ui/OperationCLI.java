package com.excilys.cdb.ui;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;

public class OperationCLI {

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

	public static void showComputerCLI() throws NotFoundException{
		
		int id=ReaderCLI.choixID();
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		System.out.println("Voici les informations demandées: ");
		System.out.println(oc.get());
	
	}

	public static void updateComputerCLI() throws NotFoundException {
		
		Computer c=ReaderCLI.choixComputer();
		System.out.println("Données mise à jour: ");
		System.out.println(c);
		ComputerDAO.updateComputer(c);
		
	}

	public static void createComputerCLI() throws NotFoundException{
		
		Computer c=ReaderCLI.choixComputer();
		ComputerDAO.createComputer(c);
		System.out.println("Ordinateur crée: ");
		System.out.println(c);
		
	}

	public static void deleteComputerCLI() throws NotFoundException{
		
		System.out.println("Computer ID:");
		int id=Integer.parseInt(SC.nextLine());
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		if(oc.isEmpty()) {
			throw new NotFoundException("Computer Not Found");
		}
		ComputerDAO.deleteComputer(id);
	}
	
}
