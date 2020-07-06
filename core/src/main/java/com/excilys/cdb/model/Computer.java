package com.excilys.cdb.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Computer")
@Table(name="computer")
public final class Computer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="introduced", nullable=true)
	private LocalDateTime introductDate;
	
	@Column(name="discontinued", nullable=true)
	private LocalDateTime discontinueDate;
	
	@ManyToOne @JoinColumn(name="company_id")
	private Company company;
		
	public Computer() {}
	
	private Computer(Builder cb) {
		this.name=cb.name;
		this.id=cb.id;
		this.introductDate=cb.introductDate;
		this.discontinueDate=cb.discontinueDate;
		this.company=cb.company;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
		
	public LocalDateTime getIntroductDate() {
		return introductDate;
	}

	public LocalDateTime getDiscontinueDate() {
		return discontinueDate;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroductDate(LocalDateTime introductDate) {
		this.introductDate = introductDate;
	}

	public void setDiscontinueDate(LocalDateTime discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	public void setCompany(Company company) {
		this.company = company;
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
		res+=this.company==null?"non définie":this.company.getId();
		
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Computer))
			return false;
		Computer other = (Computer) obj;
		if (discontinueDate == null) {
			if (other.discontinueDate != null)
				return false;
		} else if (!discontinueDate.equals(other.discontinueDate))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (id != other.id)
			return false;
		if (introductDate == null) {
			if (other.introductDate != null)
				return false;
		} else if (!introductDate.equals(other.introductDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static class Builder{
		
		private final String name;
		private int id;
		private LocalDateTime introductDate;
		private LocalDateTime discontinueDate;
		private Company company;

		public Builder(String name) {
			this.name=name;
		}

		public Builder setId(int id) {
			this.id=id;
			return this;
		}
		
		public Builder setIntroductDate(LocalDateTime introductDate) {
			this.introductDate = introductDate;
			return this;
		}

		public Builder setDiscontinueDate(LocalDateTime discontinueDate) {
			this.discontinueDate = discontinueDate;
			return this;
		}

		public Builder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
		
	}

}
