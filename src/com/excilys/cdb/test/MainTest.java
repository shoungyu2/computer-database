package com.excilys.cdb.test;

import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.CompanieService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.VerificationService;
import com.excilys.cdb.ui.LauncherCLI;
import com.excilys.cdb.ui.OperationCLI;
import com.excilys.cdb.ui.ReaderCLI;

public class MainTest {

	public static void main(String[] args) {
		
		injectionDepdencies();
		LauncherCLI.faitesVotreChoix();
		
	}
	
	public static void injectionDepdencies() {
		
		Mapper map=new Mapper();
		
		ReaderCLI rCLI=new ReaderCLI();
		OperationCLI opCLI=new OperationCLI();
		
		ComputerService computerService=new ComputerService();
		CompanieService companieService=new CompanieService();
		VerificationService verifService=new VerificationService();
		
		ComputerDAO computerDAO= new ComputerDAO();
		CompanieDAO companieDAO= new CompanieDAO();
		
		verifService.setCompanieDAO(companieDAO);
		verifService.setComputerDAO(computerDAO);
		
		computerService.setCompDAO(computerDAO);
		computerService.setVerifServ(verifService);
		computerService.setMap(map);
		
		companieService.setCompDAO(companieDAO);
		companieService.setVerifServ(verifService);
		companieService.setMap(map);
		
		opCLI.setCompanieServ(companieService);
		opCLI.setComputerServ(computerService);
		opCLI.setRcli(rCLI);
		
		LauncherCLI.setOpCLI(opCLI);
		
	}
	
}
