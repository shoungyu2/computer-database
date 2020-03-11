package com.excilys.cdb.ui;

import java.util.Scanner;

import com.excilys.cdb.exception.NotFoundException;

public class LauncherCLI {
	
	private final static Scanner SC=new Scanner(System.in);	
	
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
	
	private static boolean revenirMenu(boolean ok) {
		
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
					OperationCLI.listComputerCLI();
					ok=revenirMenu(ok);
					break;
				
				case 2:
					OperationCLI.listCompanieCLI();
					ok=revenirMenu(ok);
					break;
					
				case 3:
					OperationCLI.showComputerCLI();
					ok=revenirMenu(ok);
					break;
					
				case 4:
					OperationCLI.createComputerCLI();
					ok=revenirMenu(ok);
					break;
					
				case 5:
					OperationCLI.updateComputerCLI();
					ok=revenirMenu(ok);
					break;
				
				case 6:
					OperationCLI.deleteComputerCLI();
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
	
	@Override
	public void finalize() {
		SC.close();
	}
	
}
