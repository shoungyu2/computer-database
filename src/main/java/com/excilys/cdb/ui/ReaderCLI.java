package com.excilys.cdb.ui;

import java.util.Scanner;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;


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
	
	public CompanyDTO choixCompanie() {

		System.out.println("Choisissez votre Companie: ");
		String compID=choixID();
		return new CompanyDTO.CompanyDTOBuilder(compID).build();
		
	}
	
	public ComputerDTO choixComputerForUpdate() {
		
		ComputerDTO cdto;
		String id=choixID();
		System.out.println("Nom de l'ordinateur");
		String name=choixNom();
		System.out.println("Date d'introduction");
		String introduced=choixDate();
		System.out.println("Date de retrait");
		String discontinued=choixDate();
		CompanyDTO companyDTO=choixCompanie();
		cdto=new ComputerDTO.Builder(id, name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO)
				.build();
		return cdto;
		
	}
	
	public ComputerDTO choixComputerForCreate() {
		
		ComputerDTO cdto;
		System.out.println("Nom de l'ordinateur");
		String name=choixNom();
		System.out.println("Date d'introduction");
		String introduced=choixDate();
		System.out.println("Date de retrait");
		String discontinued=choixDate();
		CompanyDTO companyDTO=choixCompanie();
		cdto=new ComputerDTO.Builder("-1", name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO)
				.build();
		return cdto;
		
	}
	
		
}
