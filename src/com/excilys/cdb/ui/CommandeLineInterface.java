package com.excilys.cdb.ui;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

/**
 * Classe implémentant une CLI pour les différentes opérations de la BDD
 * @author masterchief
 */
public class CommandeLineInterface {

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
	
	public static void showComputerCLI() {
		
		System.out.println("Computer ID");
		String str=SC.nextLine();
		int id=Integer.parseInt(str);
		
		new ComputerDAO();
		Optional<Computer> oc=ComputerDAO.showDetailComputer(id);
		System.out.println("Voici les informations demandées: ");
		System.out.println(oc.get());
	
	}
}
