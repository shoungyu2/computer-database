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
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.AllServices;

@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 3345158907466408519L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=getServletContext();
		Object obj= sc.getAttribute("AllServices");
		
		if(obj instanceof AllServices) {
			
			AllServices allServices=(AllServices) obj;
			
			String computerId=request.getParameter("computerId");
			String computerName=request.getParameter("computerName");
			String introduced=request.getParameter("introduced");
			String discontinued=request.getParameter("discontinued");
			String companyId=request.getParameter("companyId");
			
			Optional<Company> oComp=Optional.empty();
			
			try {
				oComp=allServices.getCompanyService().showDetailCompanyService(companyId);
			} catch (InvalidEntryException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Company comp=oComp.get();
			String companyName=comp.getName();
			CompanyDTO companyDTO=new CompanyDTO.CompanyDTOBuilder(companyId)
					.setName(companyName).build();
			
			
			ComputerDTO compDTO=new ComputerDTO.ComputerDTOBuilder(computerId, computerName)
					.setIntroduced(introduced)
					.setDiscontinued(discontinued)
					.setCompanyDTO(companyDTO)
					.build();
			
			try {
				allServices.getComputerService().updateComputerService(compDTO);
				request.setAttribute("success", "Computer successfully edited");
			} catch (InvalidEntryException e) {
				List<Problems> listProb=e.getListProb();
				String errors="";
				for(Problems pb: listProb) {
					errors+=pb.toString()+"\n";
				}
				request.setAttribute("errors", errors);
			} catch (ComputerIsNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new ServletException("Bad context: the attribute \\\"AllServices\\\" is wrong");
		}
		
		int numPage=Integer.parseInt(request.getParameter("numPage"));
		int nbrPage=Integer.parseInt(request.getParameter("nbrPage"));
		
		request.setAttribute("numPage", numPage);
		request.setAttribute("nbrPage", nbrPage);
		
		doGet(request,response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String computerId=request.getParameter("computerId");
		request.setAttribute("computerId", computerId);
		
		int numPage=Integer.parseInt(request.getParameter("numPage"));
		int nbrPage=Integer.parseInt(request.getParameter("nbrPage"));
		
		request.setAttribute("numPage", numPage);
		request.setAttribute("nbrPage", nbrPage);
		
		ServletContext sc=getServletContext();
		Object obj=sc.getAttribute("AllServices");
		if(obj instanceof AllServices) {
			AllServices allServices=(AllServices) obj;
			try {
				Optional<Computer> oc=allServices.getComputerService().showDetailComputerService(computerId);
				Mapper map=allServices.getMap();
				Computer c=oc.isEmpty()?null : oc.get();
				if(c!=null) {
					request.setAttribute("computerName", c.getName());
					request.setAttribute("introducedDate",map.dateToString(c.getIntroductDate()));
					request.setAttribute("discontinuedDate",map.dateToString(c.getDiscontinueDate()));
					CompanyDTO comp= map.companyToString(c.getCompany());
					if(comp!=null) {
						request.setAttribute("companyId", comp.getId());
					}
				}
			}catch (InvalidEntryException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				List<Company> listComp=allServices.getCompanyService().listCompanyService();
			request.setAttribute("listCompany", listComp);
		}
		else {
			throw new ServletException("Bad context: the attribute \\\"AllServices\\\" is wrong");
		}
		
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
		rd.forward(request, response);
		
	}

}
