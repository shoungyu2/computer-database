package com.excilys.cdb.service;

public class AllServices {

	private ComputerService computerService;
	private CompanyService companyService;
	
	public ComputerService getComputerService() {
		return computerService;
	}
	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
}
