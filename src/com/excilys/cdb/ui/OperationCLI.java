package com.excilys.cdb.ui;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.exception.DateInvalideException;
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

	public static void showComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			String id= ReaderCLI.choixID();
			try {
				int idComp=Integer.parseInt(id);
				System.out.println(ComputerService.showDetailComputerService(idComp));
				ok=true;
			} catch(NotFoundException nfe) {
				System.out.println("L'id n'est pas dans la BDD");
			}
			catch(NumberFormatException nfe) {
				System.out.println("ID invalide");
			}
		}
	
	}

	public static void updateComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			List<String> infoComp=ReaderCLI.choixComputerForUpdate();
			try {
				ComputerService.updateComputerService(infoComp);
				ok=true;
			} catch (NotFoundException nfe) {
				System.out.println("L'ID n'est pas dans la base");
			} catch (NumberFormatException nfe) {
				System.out.println("ID invalide");
			} catch (NameIsNullException nine) {
				System.out.println("Le nom est obligatoire");
			} catch (DateInvalideException die) {
				System.out.println("La date d'introduction doit être avant la date de retrait");
			} catch (DateTimeParseException dtpe) {
				System.out.println("Date invalide");
			}
		}
		
	}

	public static void createComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			List<String> infoComp=ReaderCLI.choixComputerForCreate();
			try {
				ComputerService.createComputerService(infoComp);
				ok=true;
			} catch (NotFoundException nfe) {
				System.out.println("L'ID n'est pas dans la base");
			} catch (NumberFormatException nfe) {
				System.out.println("ID invalide");
			} catch (NameIsNullException nine) {
				System.out.println("Le nom est obligatoire");
			} catch (DateInvalideException die) {
				System.out.println("La date d'introduction doit être avant la date de retrait");
			} catch (DateTimeParseException dtpe) {
				System.out.println("Date invalide");
			}
		}
		
	}

	public static void deleteComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			System.out.println("Computer ID:");
			try {
				int id=Integer.parseInt(ReaderCLI.choixID());
				ComputerService.deleteComputer(id);
			} catch(NotFoundException nfe) {
				System.out.println("l'ID n'est pas dans la base");
			} catch(NumberFormatException nfe) {
				System.out.println("ID invalide");
			}
		}
	
	}
	
}
