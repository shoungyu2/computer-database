package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.service.AllServices;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 909640541027872558L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String computerName=request.getParameter("computerName");
		String introduced=request.getParameter("introduced");
		String discontinued=request.getParameter("discontinued");
		
		ComputerDTO compDTO=new ComputerDTO.ComputerDTOBuilder("-1", computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(null)
				.build();
		
		ServletContext sc=getServletContext();
		Object obj= sc.getAttribute("AllServices");
		if(obj instanceof AllServices) {
			AllServices allServices=(AllServices) obj;
			try {
				allServices.getComputerService().createComputerService(compDTO);
			} catch (InvalidEntryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ComputerIsNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new ServletException("Bad context: the attribute \\\"AllServices\\\" is wrong");
		}
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
	
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
	
			
	}
	
}
