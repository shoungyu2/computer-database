package com.excilys.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.exception.DateInvalideException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NameIsNullException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanieService;
import com.excilys.cdb.service.ComputerService;

public class OperationCLI {

	private final static Scanner SC=new Scanner(System.in);

	public static void listComputerCLI() {
			
		List<Computer> listComp=ComputerService.listComputerService();
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
		
		List<Companie> listComp=CompanieService.listCompanieService();
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
		
		System.out.println("Computer ID:");
		int id=ReaderCLI.choixID();
		Computer c=ComputerService.showDetailComputerService(id);
		System.out.println("Voici les informations demandées: ");
		System.out.println(c);
	
	}

	public static void updateComputerCLI() throws InvalidEntryException, DateInvalideException, NameIsNullException, NotFoundException {
		
		Computer c=ReaderCLI.choixComputerForUpdate();
		ComputerService.updateComputerService(c);
		System.out.println("Données mise à jour: ");
		System.out.println(c);
		
	}

	public static void createComputerCLI() throws DateInvalideException, NameIsNullException, NotFoundException{
		
		Computer c=ReaderCLI.choixComputerForCreate();
		ComputerService.createComputerService(c);
		System.out.println("Ordinateur crée: ");
		System.out.println(c);
		
	}

	public static void deleteComputerCLI() throws NotFoundException{
		
		System.out.println("Computer ID:");
		int id=ReaderCLI.choixID();
		ComputerService.deleteComputer(id);
	
	}
	
}
