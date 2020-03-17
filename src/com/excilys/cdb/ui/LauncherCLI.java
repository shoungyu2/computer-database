package com.excilys.cdb.ui;

import java.util.Scanner;

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
	
	public static void faitesVotreChoix() {
		
		boolean ok=false;
		
		while(!ok) {
			
			afficheMenu();
			try {
				int choix=Integer.parseInt(SC.nextLine());
				switch(choix) {
				
				case 1:
					opCLI.listComputerCLI();
					ok=revenirMenu();
					break;
				
				case 2:
					opCLI.listCompanieCLI();
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
