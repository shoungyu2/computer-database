package com.excilys.cdb.model;


/**
 * Classe représentant un manufactureur de PC
 * @author masterchief
 */
public class Companie {
	
	/**
	 * Le nom de l'entreprise
	 */
	private final String name;
	
	/**
	 * L'identificateur de l'entreprise
	 */
	private final int id;
	
	/**
	 * Le Constructeur de la classe companie
	 * @param name le nom de l'entreprise
	 * @param id l'ID de l'entreprise
	 */
	public Companie(String name,int id) {
		this.name=name;
		this.id=id;
	}
	
	public String getName() {
		return this.name;
		}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "ID: "+id+" Nom: "+name;
	}
}
