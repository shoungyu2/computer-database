package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.AllServices;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 909640541027872558L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AllServices allServices=MethodServlet.getAllServices(request);
		
		CompanyDTO companyDTO=MethodServlet.getCompanyDTOFromRequest(request, allServices);
		ComputerDTO computerDTO=MethodServlet.getComputerDTOFromRequest(request, "-1", companyDTO);
		
		MethodServlet.createOrUpdateComputer(request, allServices, computerDTO, true);
	
		doGet(request, response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=getServletContext();
		
		Object obj= sc.getAttribute("AllServices");
		if(obj instanceof AllServices) {
			AllServices allServices=(AllServices) obj;
			List<Company> listComp=allServices.getCompanyService().listCompanyService();
			request.setAttribute("listCompany", listComp);
		}
		else {
			throw new ServletException("Bad context: the attribute \\\"AllServices\\\" is wrong");
		}
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
			
	}
	
}
