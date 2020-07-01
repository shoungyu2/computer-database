package com.excilys.cdb.model;

import org.springframework.stereotype.Component;

@Component
public final class Company {
	
	private final String name;
	private final int id;
	
	private Company(Builder build) {
		this.name=build.name;
		this.id=build.id;
	}
	
	public String getName() {
		return this.name;
		}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Company))
			return false;
		Company other = (Company) obj;
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
	
	public static class Builder{
		
		private final int id;
		private String name;
		
		public Builder(int id) {
			this.id=id;
		}
		
		public Builder setName(String name) {
			this.name=name;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
		
	}
}
