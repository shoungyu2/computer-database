package com.excilys.cdb.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReaderCLI {

	private final static Scanner SC=new Scanner(System.in);
	
	public String choixID(){

		System.out.println("Choisissez l'ID voulu: ");
		return SC.nextLine();
		
	}
	
	public String choixNom() {
		
		return SC.nextLine();
		
	}
	
	public String choixDate(){
		
		System.out.println("Choisissez une date: ");
		return SC.nextLine();
		
	}
	
	public String choixCompanie() {

		System.out.println("Choisissez votre Companie: ");
		return choixID();
		
	}
	
	public List<String> choixComputerForUpdate() {
		
		List<String> res=new ArrayList<String>();
		res.add(choixID());
		System.out.println("Nom de l'odinateur");
		res.add(choixNom());
		System.out.println("Date d'introduction");
		res.add(choixDate());
		System.out.println("Date de retrait");
		res.add(choixDate());
		res.add(choixCompanie());
		return res;
		
	}
	
	public List<String> choixComputerForCreate() {
		
		List<String> res=new ArrayList<String>();
		res.add("0");
		System.out.println("Nom de l'odinateur");
		res.add(choixNom());
		System.out.println("Date d'introduction");
		res.add(choixDate());
		System.out.println("Date de retrait");
		res.add(choixDate());
		res.add(choixCompanie());
		return res;
		
	}
	
		
}
