package com.excilys.cdb.dto;

public final class ComputerDTO {
	
	private final String id;
	private final String name;
	private final String introduced;
	private final String discontinued;
	private final CompanyDTO companyDTO;
	
	private ComputerDTO(Builder cdtob) {
		
		this.id=cdtob.id;
		this.name=cdtob.name;
		this.introduced=cdtob.introduced;
		this.discontinued=cdtob.discontinued;
		this.companyDTO=cdtob.companyDTO;
		
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}
	
	@Override
	public String toString() {
		String companyDTOString=companyDTO==null?"":companyDTO.toString();
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyDTO=" + companyDTOString + "]";
	}

	public static class Builder{
		
		private final String id;
		private final String name;
		private String introduced;
		private String discontinued;
		private CompanyDTO companyDTO;
		
		public Builder(String id, String name) {
			
			this.id=id;
			this.name=name;
			
		}
		
		public Builder setIntroduced(String introduced) {
			this.introduced=introduced;
			return this;
		}
		
		public Builder setDiscontinued(String discontinued) {
			this.discontinued=discontinued;
			return this;
		}
		
		public Builder setCompanyDTO(CompanyDTO cdto) {
			this.companyDTO=cdto;
			return this;
		}
		
		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}
	
}
