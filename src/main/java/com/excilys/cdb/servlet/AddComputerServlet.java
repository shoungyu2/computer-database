package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.AllServices;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 909640541027872558L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ServletContext sc=getServletContext();
		Object obj= sc.getAttribute("AllServices");
		
		if(obj instanceof AllServices) {
			
			AllServices allServices=(AllServices) obj;
			
			String computerName=request.getParameter("computerName");
			String introduced=request.getParameter("introduced");
			String discontinued=request.getParameter("discontinued");
			String companyId=request.getParameter("companyId");
			
			Optional<Company> oComp=Optional.empty();
			
			try {
				oComp=allServices.getCompanyService().showDetailCompanyService(companyId);
			} catch (InvalidEntryException e1) {
				String errorMessage="";
				for(Problems pb : e1.getListProb()) {
					errorMessage+=pb.toString()+"\n";
				}
				request.setAttribute("errors", errorMessage);
			}
			
			CompanyDTO companyDTO=null;
			if (oComp.isPresent()) {
				companyDTO=allServices.getMap().companyToString(oComp.get());
			}
			
			ComputerDTO compDTO=new ComputerDTO.ComputerDTOBuilder("-1", computerName)
					.setIntroduced(introduced)
					.setDiscontinued(discontinued)
					.setCompanyDTO(companyDTO)
					.build();
			
			try {
				allServices.getComputerService().createComputerService(compDTO);
				request.setAttribute("success", "Computer successfully added");
			} catch (InvalidEntryException e) {
				List<Problems> listProbs=e.getListProb();
				String errorMessage="";
				for(Problems pb:listProbs) {
					errorMessage+=pb.toString()+"\n";
				}
				request.setAttribute("errors", errorMessage);
			} catch (ComputerIsNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new ServletException("Bad context: the attribute \\\"AllServices\\\" is wrong");
		}
	
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
