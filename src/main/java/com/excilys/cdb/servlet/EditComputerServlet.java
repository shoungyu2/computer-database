package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.spring.SpringConfiguration;

@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 3345158907466408519L;
	
	private static AnnotationConfigApplicationContext context=SpringConfiguration.getContext();
	
	private ComputerService computerService=context.getBean(ComputerService.class);
	private CompanyService companyService=context.getBean(CompanyService.class);
	private Mapper mapper=context.getBean(Mapper.class);
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CompanyDTO companyDTO=MethodServlet.getCompanyDTOFromRequest(request, companyService, mapper);
		String computerId=request.getParameter("computerId");
		ComputerDTO computerDTO=MethodServlet.getComputerDTOFromRequest(request, computerId, companyDTO);		
			
		if(MethodServlet.createOrUpdateComputer(request, computerService, computerDTO, false)) {
			int numPage=Integer.parseInt(request.getParameter("numPage"));
			int nbrPage=Integer.parseInt(request.getParameter("nbrPage"));
			
			request.setAttribute("numPage", numPage);
			request.setAttribute("nbrPage", nbrPage);
			
			doGet(request,response);
		}
		else {
			RequestDispatcher rd=request.getRequestDispatcher("/ErrorServlet");
			rd.forward(request, response);
		}
		
	}
	
	private String setAllAttribute(HttpServletRequest request) {
		
		String computerId=request.getParameter("computerId");
		request.setAttribute("computerId", computerId);
		request.setAttribute("numPage", Integer.parseInt(request.getParameter("numPage")));
		request.setAttribute("nbrPage", Integer.parseInt(request.getParameter("nbrPage")));
		request.setAttribute("listCompany", companyService.listCompanyService());
		return computerId;
		
	}
	
	private void setComputerInRequest(HttpServletRequest request, Computer computer) {
		
		if(computer!=null) {
			request.setAttribute("computerName", computer.getName());
			request.setAttribute("introducedDate",mapper.dateToString(computer.getIntroductDate()));
			request.setAttribute("discontinuedDate",mapper.dateToString(computer.getDiscontinueDate()));
			CompanyDTO comp= mapper.companyToString(computer.getCompany());
			if(comp!=null) {
				request.setAttribute("companyId", comp.getId());
			}
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String computerId=setAllAttribute(request);
		
		try {
			Optional<Computer> oc=computerService.getComputerFromIdService(computerId);
			Computer c=oc.isEmpty()?null : oc.get();
			setComputerInRequest(request, c);
		} catch (InvalidEntryException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
		rd.forward(request, response);
		
	}

}
