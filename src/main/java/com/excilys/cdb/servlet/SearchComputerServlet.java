package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.AllServices;

@WebServlet("/SearchComputerServlet")
public class SearchComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -2389930487225371260L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=request.getServletContext();
		Object obj= sc.getAttribute("AllServices");
		
		if(obj instanceof AllServices) {
			
			AllServices allServices=(AllServices) obj;
			
			Page page=new Page(1);
			Page.setNbrElements(20);
			String search=request.getParameter("search");
			int nbrComputer=allServices.getComputerService().getNbrComputerInSearchService(search);
			int nbrPages=nbrComputer/Page.getNbrElements();
			if(nbrComputer%Page.getNbrElements()!=0) {
				nbrPages++;
			}
			Page.setNbrPages(nbrPages);
			request.setAttribute("pcCount", nbrComputer);
			request.setAttribute("pcList", allServices.getComputerService().searchComputerService(search, page));
			request.setAttribute("nbrPage", Page.getNbrPages());
			
		}
		else {
			throw new ServletException("Bad context, the attribute \"AllServices\" is wrong");
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		
	}
	
}
