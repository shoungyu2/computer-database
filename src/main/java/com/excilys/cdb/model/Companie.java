package com.excilys.cdb.model;



public class Companie {
	
	private final String name;
	private final int id;
	
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
	public boolean equals(Object obj) {
		
		if(obj==null) {
			return false;
		}
		
		if(!(obj instanceof Companie)) {
			return false;
		}
		else {
			Companie objComp=(Companie) obj;
			if (objComp.getId()==this.id && objComp.getName().equals(this.name)) {
				return true;
			}
			else {
				return false;
			}
		}
		
	}
	
	@Override
	public String toString() {
		return "ID: "+id+" Nom: "+name;
	}
}
