package com.excilys.cdb.injection;

import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.VerificationService;
import com.excilys.cdb.ui.LauncherCLI;
import com.excilys.cdb.ui.OperationCLI;
import com.excilys.cdb.ui.ReaderCLI;

public class InjectionCLI {

	public static void injectionDepdencies() {	
		
		ReaderCLI rCLI=new ReaderCLI();
		OperationCLI opCLI=new OperationCLI();
		
		ComputerService computerService=new ComputerService();
		CompanyService companyService=new CompanyService();
		VerificationService verifService=new VerificationService();
		
		Mapper map=new Mapper();

		ComputerDAO computerDAO= new ComputerDAO();
		CompanyDAO companyDAO= new CompanyDAO();
		
		verifService.setComputerDAO(computerDAO);
		verifService.setCompanyDAO(companyDAO);
		
		companyService.setCompDAO(companyDAO);
		companyService.setVerifServ(verifService);
		companyService.setMap(map);
		
		opCLI.setCompanieServ(companyService);
		opCLI.setComputerServ(computerService);
		opCLI.setRcli(rCLI);
		
		LauncherCLI.setOpCLI(opCLI);
		
	}

	
}
