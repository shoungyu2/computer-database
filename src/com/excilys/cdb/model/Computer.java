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
	private Companie entreprise;
	
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
		this.entreprise=cb.entreprise;
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

	public LocalDateTime getDiscontinueDate() {
		return discontinueDate;
	}
	
	public Companie getEntreprise() {
		return entreprise;
	}
	
	public void setEntreprise(Companie entreprise) {
		this.entreprise = entreprise;
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
		res+=this.entreprise==null?"non définie":this.entreprise.getId();
		
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
		
		private Companie entreprise;
		
		/**
		 * Constructeur de la classe Builder prenant nom et ID en paramètre
		 * @param name le nom de l'ordinateur
		 * @param id l'ID de l'ordinateur
		 */
		public ComputerBuilder(String name, int id) {
			this.name=name;
			this.id=id;
		}
		
		/**
		 * Constructeur de la classe Builder ne prenant que le nom en paramètre
		 * @param name le nom de l'ordinateur
		 */
		public ComputerBuilder(String name) {
			this.name=name;
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

		public Companie getEntreprise() {
			return entreprise;
		}

		public ComputerBuilder setEntreprise(Companie entreprise) {
			this.entreprise = entreprise;
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
