package com.excilys.cdb.test;


import java.util.NoSuchElementException;
import java.util.Optional;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.ui.CommandeLineInterface;;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Optional<Computer> oc;
		Companie company=new Companie("Apple Inc.",1);
		Companie company2=new Companie("Thinking Machines",2);
		Computer c= new Computer.ComputerBuilder("iPhone 4S", 574).setEntreprise(company).build();
		
		/*//Test de listComputer
		System.out.println("Test de listComputer");
		for(Computer comp: ComputerDAO.listComputer()) {
			System.out.println(comp);
			System.out.println();
		}
		
		//Test de showDetailComputer
		System.out.println("Test de showDetailComputer");
		oc=ComputerDAO.showDetailComputer(573);
		System.out.println(oc.get());
		System.out.println();
		
		//test de createComputer
		System.out.println("Test de createComputer");
		ComputerDAO.createComputer(c);
		oc=ComputerDAO.showDetailComputer(574);
		System.out.println(oc.get());
		System.out.println();
		
		// Test de updateComputer
		System.out.println("Test de updateComputer");
		c= new Computer.ComputerBuilder("iPhone 4S", 574).setEntreprise(company2).build();
		ComputerDAO.updateComputer(c);
		oc=ComputerDAO.showDetailComputer(574);
		try {
			System.out.println(oc.get());
		}
		catch(NoSuchElementException nsee) {
			System.out.println("Not Found");
		}
		System.out.println();
		
		// Test de deleteComputer
		ComputerDAO.deleteComputer(574);
		oc=ComputerDAO.showDetailComputer(574);
		try {
			System.out.println(oc.get());
		}
		catch(NoSuchElementException nsee) {
			System.out.println("Not Found");
		}
		System.out.println();
		*/
		
		
		//Test de showComputerCLI
		//CommandeLineInterface.showComputerCLI();
		
		//Test de listComputerCLI
		//CommandeLineInterface.listComputerCLI();
		
		//Test de updateComputerCLI
		oc=ComputerDAO.showDetailComputer(573);
		System.out.println(oc.get());
		try {
			CommandeLineInterface.updateComputerCLI();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oc=ComputerDAO.showDetailComputer(573);
		System.out.println(oc.get());
	}

}
