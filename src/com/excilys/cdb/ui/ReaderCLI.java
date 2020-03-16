package com.excilys.cdb.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReaderCLI {

	private final static Scanner SC=new Scanner(System.in);
	
	public static String choixID(){

		System.out.println("Choisissez l'ID voulu: ");
		return SC.nextLine();
		
	}
	
	public static String choixNom() {
		
		return SC.nextLine();
		
	}
	
	public static String choixDate(){
		
		System.out.println("Choisissez une date: ");
		return SC.nextLine();
		
	}
	
	public static String choixCompanie() {

		System.out.println("Choisissez votre Companie: ");
		return choixID();
		
	}
	
	public static List<String> choixComputerForUpdate() {
		
		List<String> res=new ArrayList<String>();
		res.add(ReaderCLI.choixID());
		System.out.println("Nom de l'odinateur");
		res.add(ReaderCLI.choixNom());
		System.out.println("Date d'introduction");
		res.add(ReaderCLI.choixDate());
		System.out.println("Date de retrait");
		res.add(ReaderCLI.choixDate());
		res.add(ReaderCLI.choixCompanie());
		return res;
	}
	
	public static List<String> choixComputerForCreate() {
		
		List<String> res=new ArrayList<String>();
		res.add("0");
		System.out.println("Nom de l'odinateur");
		res.add(ReaderCLI.choixNom());
		System.out.println("Date d'introduction");
		res.add(ReaderCLI.choixDate());
		System.out.println("Date de retrait");
		res.add(ReaderCLI.choixDate());
		res.add(ReaderCLI.choixCompanie());
		return res;
		
	}
	
		
}
