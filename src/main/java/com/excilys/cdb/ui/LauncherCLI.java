package com.excilys.cdb.ui;

import java.util.Scanner;
import org.apache.log4j.Logger;

import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.VerificationService;

public class LauncherCLI {
	
	private final static Logger LOGGER=Logger.getLogger(LauncherCLI.class);
	
	private final static Scanner SC=new Scanner(System.in);
	private static OperationCLI opCLI;
	
	public static void setOpCLI(OperationCLI operatCLI) {
		
		opCLI=operatCLI;
		
	}
	
	public static void main(String[] args) {
	
		injectionDepdencies();
		Page.setNbrPages(573/20+1);
		LauncherCLI.faitesVotreChoix();
		
	}
	
public static void injectionDepdencies() {
		
		
		ReaderCLI rCLI=new ReaderCLI();
		OperationCLI opCLI=new OperationCLI();
		
		ComputerService computerService=new ComputerService();
		CompanyService companyService=new CompanyService();
		VerificationService verifService=new VerificationService();
		
		Mapper map=new Mapper();

		ComputerDAO computerDAO= new ComputerDAO();
		CompanyDAO companyDAO= new CompanyDAO();
		
		verifService.setComputerDAO(computerDAO);
		verifService.setCompanyDAO(companyDAO);
		
		computerService.setCompDAO(computerDAO);
		computerService.setVerifServ(verifService);
		computerService.setMap(map);
		
		companyService.setCompDAO(companyDAO);
		companyService.setVerifServ(verifService);
		companyService.setMap(map);
		
		opCLI.setCompanieServ(companyService);
		opCLI.setComputerServ(computerService);
		opCLI.setRcli(rCLI);
		
		LauncherCLI.setOpCLI(opCLI);
		
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
					
				case 7:
					System.out.println("Au revoir");
					ok=true;
					
				default:
					throw new NumberFormatException();
					
				}
			} catch (NumberFormatException nfe) {
				LOGGER.error("Veuillez choisir un nombre entre 1 et 7");
			}
			
		}
	}
	
	@Override
	public void finalize() {
		SC.close();
	}
	
}
