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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Companie))
			return false;
		Companie other = (Companie) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ID: "+id+" Nom: "+name;
	}
}
