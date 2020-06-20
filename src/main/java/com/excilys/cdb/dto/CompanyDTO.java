package com.excilys.cdb.dto;

public final class CompanyDTO {

	private final String id;
	private final String name;
	
	private CompanyDTO(CompanyDTOBuilder cdtob) {
		this.id=cdtob.id;
		this.name=cdtob.name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CompanyDTO))
			return false;
		CompanyDTO other = (CompanyDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static class CompanyDTOBuilder{
		
		private final String id;
		private String name;
		
		public CompanyDTOBuilder(String id) {
			this.id=id;
		}
		
		public CompanyDTOBuilder setName(String name) {
			this.name=name;
			return this;
		}
		
		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
		
	}
	
}
