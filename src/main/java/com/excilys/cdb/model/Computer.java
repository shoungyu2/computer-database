package com.excilys.cdb.model;

import java.time.LocalDateTime;


public class Computer {
	
	private final String name;
	private final int id;
	private final LocalDateTime introductDate;
	private final LocalDateTime discontinueDate;
	private final Companie entreprise;
	
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

	public int getID() {
		return id;
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
	
	
	public static class ComputerBuilder{
		
		private String name;
		private int id;
		private LocalDateTime introductDate;
		private LocalDateTime discontinueDate;
		private Companie entreprise;

		public ComputerBuilder(String name, int id) {
			this.name=name;
			this.id=id;
		}

		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder setIntroductDate(LocalDateTime introductDate) {
			this.introductDate = introductDate;
			return this;
		}

		public ComputerBuilder setDiscontinueDate(LocalDateTime discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}

		public ComputerBuilder setEntreprise(Companie entreprise) {
			this.entreprise = entreprise;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
		
	}

	
	
}
