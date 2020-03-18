package com.excilys.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanieService;
import com.excilys.cdb.service.ComputerService;

public class OperationCLI {

	private final static Scanner SC=new Scanner(System.in);
	private ComputerService computerServ;
	private CompanieService companieServ;
	private ReaderCLI rcli;
	
	public void setComputerServ(ComputerService computerServ) {
		this.computerServ = computerServ;
	}

	public void setCompanieServ(CompanieService companieServ) {
		this.companieServ = companieServ;
	}

	public void setRcli(ReaderCLI rcli) {
		this.rcli = rcli;
	}

	public void listComputerCLI() {
			
		List<Computer> listComp=computerServ.listComputerService();
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
	
	public void listCompanieCLI() {
		
		List<Companie> listComp=companieServ.listCompanieService();
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

	public void showComputerCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(computerServ.showDetailComputerService(id).get());
		} catch (InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				System.out.println(p);
			}
		}
	}
	
	public void showCompanieCLI() {
		
		String id= rcli.choixID();
		try {
			System.out.println(companieServ.showDetailCompanieService(id).get());
		} catch(InvalidEntryException iee) {
			for(Problems p:iee.getListProb()) {
				System.out.println(p);
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
			} catch (InvalidEntryException iee) {
				for(Problems p: iee.getListProb()) {
					System.out.println(p);
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
			} catch(InvalidEntryException iee) {
				for (Problems p: iee.getListProb()) {
					System.out.println(p);
				}
			}
		}

	}

	public void deleteComputerCLI() {
		
		String id=rcli.choixID();
		try {
			computerServ.deleteComputerService(id);
		} catch(InvalidEntryException iee) {
			for(Problems p: iee.getListProb()) {
				System.out.println(p);
			}
		}
		
	}
	
}
