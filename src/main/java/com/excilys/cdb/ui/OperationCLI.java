package com.excilys.cdb.ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanieService;
import com.excilys.cdb.service.ComputerService;

public class OperationCLI {
	
	private final static Logger LOGGER=Logger.getLogger(OperationCLI.class);
	
	private final static Scanner SC=new Scanner(System.in);
	
	private ComputerService computerServ;
	private CompanieService companieServ;
	private ReaderCLI rcli;
	
	public OperationCLI() {
		
		try {
			FileAppender fa= new FileAppender(new PatternLayout("%d [%p] %m%n"), 
					"src/main/java/com/excilys/cdb/logger/log.txt");
			LOGGER.addAppender(fa);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public void setComputerServ(ComputerService computerServ) {
		this.computerServ = computerServ;
	}

	public void setCompanieServ(CompanieService companieServ) {
		this.companieServ = companieServ;
	}

	public void setRcli(ReaderCLI rcli) {
		this.rcli = rcli;
	}

	public void listComputerCLI(Page page) {
			
		List<Computer> listComp=computerServ.listComputerService(page);
		for(Computer c: listComp) {
			System.out.println(c);
			System.out.println();
		}
		System.out.println(page.getNumPage()+"/"+Page.getNbrPages());
		
	}
	
	public void listCompanieCLI(Page page) {
		
		List<Companie> listComp=companieServ.listCompanieService(page);
		System.out.println("Nombre de companies dans la BDD: "+listComp.size());
		System.out.println("Voulez vous les afficher(o/n)?");
		String rep=SC.nextLine();
		if(rep.equals("o")) {
			for (Companie c: listComp) {
				System.out.println(c);
				System.out.println();
			}
			System.out.println(page.getNumPage()+"/"+Page.getNbrPages());
		}
		
	}

	public void showComputerCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(computerServ.showDetailComputerService(id).get());
		} catch (InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				LOGGER.error(p);
			}
		}
	}
	
	public void showCompanieCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(companieServ.showDetailCompanieService(id).get());
		} catch(InvalidEntryException iee) {
			for(Problems p:iee.getListProb()) {
				LOGGER.error(p);
			}
		}
	}
	

	public void updateComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			List<String> infoComp=rcli.choixComputerForUpdate();
			try {
				computerServ.updateComputerService(infoComp);
				ok=true;
				LOGGER.info("Ordinateur d'id: "+infoComp.get(0)+" mis à jour");
			} catch (InvalidEntryException iee) {
				for(Problems p: iee.getListProb()) {
					LOGGER.error(p);
				}
			}
		}
		
	}

	public void createComputerCLI() {
		
		boolean ok=false;
		while(!ok) {
			List<String> infoComp=rcli.choixComputerForCreate();
			try {
				computerServ.createComputerService(infoComp);
				ok=true;
				LOGGER.info("Ajout d'un nouvel ordinateur la base de donnée:\n");
			} catch(InvalidEntryException iee) {
				for (Problems p: iee.getListProb()) {
					LOGGER.error(p);
				}
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
	
}