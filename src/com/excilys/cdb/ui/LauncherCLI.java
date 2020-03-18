package com.excilys.cdb.ui;

import java.util.Scanner;

import com.excilys.cdb.model.Page;

public class LauncherCLI {
	
	private final static Scanner SC=new Scanner(System.in);
	private static OperationCLI opCLI;
	
	public static void setOpCLI(OperationCLI operatCLI) {
		
		opCLI=operatCLI;
		
	}

	private static void afficheMenu() {
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
	
	private static boolean revenirMenu() {
		
		System.out.println("Revenir au menu ?");
		System.out.println();
		System.out.println("1. Oui");
		System.out.println("Autre entrée: Non et quitter");
		if(!(SC.nextLine().equals("1"))) {
			System.out.println("Au revoir");
			return true;
		}
		
		return false;
		
	}
	
	private static void afficherPage(Page page) {
		
		System.out.println("prec/suiv");
		String rep=SC.nextLine();
		switch(rep) {
		
		case "suiv":
			afficherPageSuivante(page);
			break;
			
		case "prec":
			afficherPagePrecedente(page);
			break;
			
		default:

		}
		
	}
	
	private static void afficherPageSuivante(Page page) {
		
		if(page.getNumPage()!=Page.getNbrPages()) {
			Page pageSuiv=new Page(page.getNumPage()+1);
			opCLI.listComputerCLI(pageSuiv);
			afficherPage(pageSuiv);
		}
		else {
			Page firstPage=new Page(0);
			opCLI.listComputerCLI(firstPage);
			afficherPage(firstPage);
		}
		
	}
	
	private static void afficherPagePrecedente(Page page) {
		
		if(page.getNumPage()!=1) {
			Page pagePrec=new Page(page.getNumPage()-1);
			opCLI.listComputerCLI(pagePrec);
			afficherPage(pagePrec);
		}
		else {
			Page lastPage=new Page(Page.getNbrPages());
			opCLI.listComputerCLI(lastPage);
			afficherPage(lastPage);
		}
		
	}
	
	public static void faitesVotreChoix() {
		
		boolean ok=false;
		
		while(!ok) {
			
			afficheMenu();
			try {
				int choix=Integer.parseInt(SC.nextLine());
				switch(choix) {
				
				case 1:
					Page firstPage=new Page(1);
					opCLI.listComputerCLI(firstPage);
					afficherPage(firstPage);
					ok=revenirMenu();
					break;
				
				case 2:
					opCLI.listCompanieCLI(new Page(1));
					ok=revenirMenu();
					break;
					
				case 3:
					opCLI.showComputerCLI();
					ok=revenirMenu();
					break;
					
				case 4:
					opCLI.createComputerCLI();
					ok=revenirMenu();
					break;
					
				case 5:
					opCLI.updateComputerCLI();
					ok=revenirMenu();
					break;
				
				case 6:
					opCLI.deleteComputerCLI();
					ok=revenirMenu();
					break;
					
				default:
					System.out.println("Au revoir");
					ok=true;
					
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Veuillez choisir un nombre entre 1 et 7");
			}
			
		}
	}
	
	@Override
	public void finalize() {
		SC.close();
	}
	
}
