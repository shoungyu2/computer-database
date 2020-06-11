package com.excilys.cdb.service;

import com.excilys.cdb.mapper.Mapper;

public class AllServices {

	private ComputerService computerService;
	private CompanyService companyService;
	private Mapper map;
	
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
	public Mapper getMap() {
		return map;
	}
	public void setMap(Mapper map) {
		this.map = map;
	}
	
}
