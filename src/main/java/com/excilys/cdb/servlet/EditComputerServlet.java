package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.AllServices;

@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 3345158907466408519L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AllServices allServices=MethodServlet.getAllServices(request);
		
		CompanyDTO companyDTO=MethodServlet.getCompanyDTOFromRequest(request, allServices);
		String computerId=request.getParameter("computerId");
		ComputerDTO computerDTO=MethodServlet.getComputerDTOFromRequest(request, computerId, companyDTO);		
			
		MethodServlet.createOrUpdateComputer(request, allServices, computerDTO, false);
	
		int numPage=Integer.parseInt(request.getParameter("numPage"));
		int nbrPage=Integer.parseInt(request.getParameter("nbrPage"));
		
		request.setAttribute("numPage", numPage);
		request.setAttribute("nbrPage", nbrPage);
		
		doGet(request,response);
		
	}
	
	private String setAllAttribute(HttpServletRequest request, AllServices allServices) {
		
		String computerId=request.getParameter("computerId");
		request.setAttribute("computerId", computerId);
		request.setAttribute("numPage", Integer.parseInt(request.getParameter("numPage")));
		request.setAttribute("nbrPage", Integer.parseInt(request.getParameter("nbrPage")));
		request.setAttribute("listCompany", allServices.getCompanyService().listCompanyService());
		return computerId;
		
	}
	
	private void setComputerInRequest(HttpServletRequest request, AllServices allServices, Computer computer) {
		
		if(computer!=null) {
			request.setAttribute("computerName", computer.getName());
			request.setAttribute("introducedDate",allServices.getMap().dateToString(computer.getIntroductDate()));
			request.setAttribute("discontinuedDate",allServices.getMap().dateToString(computer.getDiscontinueDate()));
			CompanyDTO comp= allServices.getMap().companyToString(computer.getCompany());
			if(comp!=null) {
				request.setAttribute("companyId", comp.getId());
			}
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AllServices allServices=MethodServlet.getAllServices(request);
		String computerId=setAllAttribute(request, allServices);
		
		try {
			Optional<Computer> oc=allServices.getComputerService().showDetailComputerService(computerId);
			Computer c=oc.isEmpty()?null : oc.get();
			setComputerInRequest(request, allServices, c);
		} catch (InvalidEntryException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
		rd.forward(request, response);
		
	}

}
