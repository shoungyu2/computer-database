package com.excilys.cdb.dto;

public class CompanyDTO {

	private final String id;
	private final String name;
	
	public CompanyDTO(CompanyDTOBuilder cdtob) {
		this.id=cdtob.id;
		this.name=cdtob.name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static class CompanyDTOBuilder{
		
		private String id;
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
