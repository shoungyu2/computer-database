package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.spring.SpringConfiguration;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 909640541027872558L;

	private static AnnotationConfigApplicationContext context=SpringConfiguration.getContext();
	
	private ComputerService computerService=context.getBean(ComputerService.class);
	private CompanyService companyService=context.getBean(CompanyService.class);
	private Mapper mapper=context.getBean(Mapper.class);
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		CompanyDTO companyDTO=MethodServlet.getCompanyDTOFromRequest(request, companyService, mapper);
		ComputerDTO computerDTO=MethodServlet.getComputerDTOFromRequest(request, "-1", companyDTO);
		
		if(MethodServlet.createOrUpdateComputer(request, computerService, computerDTO, true)) {
			doGet(request, response);
		}
		else {
			RequestDispatcher rd=request.getRequestDispatcher("/ErrorServlet");
			rd.forward(request, response);
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Company> listComp=companyService.listCompanyService();
		request.setAttribute("listCompany", listComp);
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
			
	}
	
}
