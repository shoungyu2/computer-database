package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.AllServices;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=request.getServletContext();
		Object obj =sc.getAttribute("AllServices");
		
		if(obj instanceof AllServices) {
			
			AllServices allServices=(AllServices) obj;
			
			String selected=request.getParameter("selection");
			String[] idSelected=selected.split(",");
			
			for(String id:idSelected) {
				
				try {
					allServices.getComputerService().deleteComputerService(id);
				} catch(InvalidEntryException ie) {
					String errorMessage="";
					for(Problems pb:ie.getListProb()) {
						errorMessage+=pb.toString()+"\n";
					}
					request.setAttribute("errors", errorMessage);
				}
				
			}
		}
		else {
			throw new ServletException("Bad context, the attribute \"AllServices\" is wrong");
		}
		
		doGet(request, response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Page page;
		String numPageStr=request.getParameter("currentPage");
		
		if(numPageStr!=null) {
			int numPage=Integer.parseInt(numPageStr);
			page=new Page(numPage);
			request.setAttribute("numPage", numPage);
		}
		else {
			page=new Page(1);
			request.setAttribute("numPage", 1);
		}
		
		String nbrElementStr=request.getParameter("nbrElement");
		if(nbrElementStr!=null) {
			int nbrElement=Integer.parseInt(nbrElementStr);
			Page.setNbrElements(nbrElement);
		}
		
		ServletContext sc= getServletContext();
		Object obj=sc.getAttribute("AllServices");
		if(obj instanceof AllServices) {
			AllServices allServices= (AllServices) obj;
			ComputerService computerService= allServices.getComputerService();
			int nbrComputer= computerService.getNbrComputerService();
			Page.setNbrPages(nbrComputer/Page.getNbrElements()+1);
			request.setAttribute("pcCount", nbrComputer);
			request.setAttribute("pcList", computerService.listComputerService(page));
			request.setAttribute("nbrPage", Page.getNbrPages());
		}
		else {
			throw new ServletException("Bad context: the attribute \"AllServices\" is wrong");
		}
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		
	}
	
}
