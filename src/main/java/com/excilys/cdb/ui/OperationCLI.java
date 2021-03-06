package com.excilys.cdb.ui;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class OperationCLI {
	
	private final static Logger LOGGER=Logger.getLogger(OperationCLI.class);
	
	private final static Scanner SC=new Scanner(System.in);
	
	private ComputerService computerServ;
	private CompanyService companieServ;
	private ReaderCLI rcli;
	
	public void setComputerServ(ComputerService computerServ) {
		this.computerServ = computerServ;
	}

	public void setCompanieServ(CompanyService companieServ) {
		this.companieServ = companieServ;
	}

	public void setRcli(ReaderCLI rcli) {
		this.rcli = rcli;
	}

	public void listComputerCLI(Page page) {
			
		List<Computer> listComp=computerServ.getComputersService("", "", "", page);
		for(Computer c: listComp) {
			System.out.println(c);
			System.out.println();
		}
		System.out.println(page.getNumPage()+"/"+Page.getNbrPages());
		
	}
	
	public void listCompanieCLI() {
		
		List<Company> listComp=companieServ.listCompanyService();
		System.out.println("Nombre de companies dans la BDD: "+listComp.size());
		System.out.println("Voulez vous les afficher(o/n)?");
		String rep=SC.nextLine();
		if(rep.equals("o")) {
			for (Company c: listComp) {
				System.out.println(c);
				System.out.println();
			}
		}
		
	}

	public void showComputerCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(computerServ.getComputerFromIdService(id).get());
		} catch (InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				LOGGER.error(p);
			}
		}
	}
	
	public void showCompanieCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(companieServ.showDetailCompanyService(id).get());
		} catch(InvalidEntryException iee) {
			for(Problems p:iee.getListProb()) {
				LOGGER.error(p);
			}
		}
	}
	

	public void updateComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			ComputerDTO infoComp=rcli.choixComputerForUpdate();
			try {
				computerServ.updateComputerService(infoComp);
				ok=true;
				LOGGER.info("Ordinateur d'id: "+infoComp.getId()+" mis à jour");
			} catch (InvalidEntryException iee) {
				for(Problems p: iee.getListProb()) {
					LOGGER.error(p);
				}
			} catch (ComputerIsNullException cine) {
				LOGGER.error("Veuillez rentrer des informations valides");
			}
		}
		
	}

	public void createComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			ComputerDTO infoComp=rcli.choixComputerForCreate();
			try {
				computerServ.createComputerService(infoComp);
				ok=true;
				LOGGER.info("Ajout d'un nouvel ordinateur la base de donnée:\n");
			} catch(InvalidEntryException iee) {
				for (Problems p: iee.getListProb()) {
					LOGGER.error(p);
				}
			} catch (ComputerIsNullException cine) {
				LOGGER.error("Veuillez rentrer des informations valides");
			}
		}

	}

	public void deleteComputerCLI() {
		
		String id=rcli.choixID();
		try {
			computerServ.deleteComputerService(id);
			LOGGER.info("Suppression de l'ordinateur d'id: "+id);
		} catch(InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				LOGGER.error(p);
			}
		}
		
	}
	
	public void deleteCompanyCLI() {
		
		String id=rcli.choixID();
		try {
			companieServ.deleteCompanyService(id);
			LOGGER.info("Supression de la companie d'id: "+id+" et de tous les ordinateurs qui lui sont rattachés");
		} catch(InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				LOGGER.error(p);
			}
		}
		
	}
}
