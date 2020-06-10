package com.excilys.cdb.injection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.AllServices;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.VerificationService;


@WebListener
public class InjectionListener implements ServletContextListener{

	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext sc= sce.getServletContext();
		
		ComputerService computerService= new ComputerService();
		CompanyService companyService= new CompanyService();
		VerificationService verifService= new VerificationService();

		AllServices aService=new AllServices();
		
		Mapper map= new Mapper();
		
		ComputerDAO computerDAO=new ComputerDAO();
		CompanyDAO companyDAO=new CompanyDAO();
		
		verifService.setCompanyDAO(companyDAO);
		verifService.setComputerDAO(computerDAO);
		
		computerService.setCompDAO(computerDAO);
		computerService.setMap(map);
		computerService.setVerifServ(verifService);
		
		companyService.setCompDAO(companyDAO);
		companyService.setMap(map);
		companyService.setVerifServ(verifService);
		
		aService.setCompanyService(companyService);
		aService.setComputerService(computerService);
		
		sc.setAttribute("AllServices", aService);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	
}
