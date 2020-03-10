package com.excilys.cdb.model;

import java.time.LocalDateTime;

/**
 * Classe décrivant un ordinateur
 * @author masterchief
 */
public class Computer {
	
	/**
	 * Le nom du PC
	 */
	private String name;
	
	/**
	 * L'id du PC
	 */
	private int id;
	
	/**
	 * La date d'introduction du PC
	 */
	private LocalDateTime introductDate;
	
	/**
	 * La date de retrait du PC
	 */
	private LocalDateTime discontinueDate;
	
	/**
	 * Le nom du manufactureur
	 */
	private int idEntreprise=-1;
	
	/**
	 * Constructeur minimale de la classe Computeur
	 * @param cb le builder contenant toutes les infos nécessaires 
	 * à la construction d'un ordinateur (Voir ComputerBuilder pour plus d'informations)
	 */
	public Computer(ComputerBuilder cb) {
		this.name=cb.name;
		this.id=cb.id;
		this.introductDate=cb.introductDate;
		this.discontinueDate=cb.discontinueDate;
		this.idEntreprise=cb.idEntreprise;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id=id;
	}
	
	public LocalDateTime getIntroductDate() {
		return introductDate;
	}

	public void setIntroductDate(LocalDateTime introductDate) {
		this.introductDate = introductDate;
	}

	public LocalDateTime getDiscontinueDate() {
		return discontinueDate;
	}
	
	public void setDiscontinueDate(LocalDateTime discontinueDate) {
		this.discontinueDate = discontinueDate;
	}
	
	public int getIDEntreprise() {
		return idEntreprise;
	}
	
	public void setIDEntreprise(int idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	@Override
	public String toString() {
		
		String res=new String();
		
		res+="ID: "+this.id;
		res+="\nNom: "+this.name;
		
		res+="\nDate d'introduction: ";
		res+=this.introductDate==null?"non définie":this.introductDate;
		
		res+="\nDate de retrait: ";
		res+=this.discontinueDate==null?"non définie":this.discontinueDate;
		
		res+="\nID du Fabricant: ";
		res+=this.idEntreprise==0?"non définie":this.idEntreprise;
		
		return res;
	}
	
	/**
	 * Builder de la classe Computer
	 * @author masterchief
	 */
	public static class ComputerBuilder{
		
		/**
		 * Tous les arguments ci-dessous sont les mêmes 
		 * que pour la classe Computer
		 */
		
		private String name;
		
		private int id;
		
		private LocalDateTime introductDate;
		
		private LocalDateTime discontinueDate;
		
		private int idEntreprise;
		
		/**
		 * Constructeur de la classe Builder
		 * @param name
		 * @param id
		 */
		public ComputerBuilder(String name, int id) {
			this.name=name;
			this.id=id;
		}

		public String getName() {
			return name;
		}

		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public int getId() {
			return id;
		}

		public ComputerBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public LocalDateTime getIntroductDate() {
			return introductDate;
		}

		public ComputerBuilder setIntroductDate(LocalDateTime introductDate) {
			this.introductDate = introductDate;
			return this;
		}

		public LocalDateTime getDiscontinueDate() {
			return discontinueDate;
		}

		public ComputerBuilder setDiscontinueDate(LocalDateTime discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}

		public int getIdEntreprise() {
			return idEntreprise;
		}

		public ComputerBuilder setIdEntreprise(int idEntreprise) {
			this.idEntreprise = idEntreprise;
			return this;
		}
		
		/**
		 * Le builder
		 * @return un nouveau Computer correspondant à l'instance courante de ComputerBuilder
		 */
		public Computer build() {
			return new Computer(this);
		}
	}

	
	
}
