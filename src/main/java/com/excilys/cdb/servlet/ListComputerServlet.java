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
import com.excilys.cdb.service.ComputerService;

@WebServlet("/ListComputerServlet")
public class ListComputerServlet extends HttpServlet{

	private static final long serialVersionUID = -3655410377159367069L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setAttribute("success", request.getParameter("success"));
		
		doGet(request,response);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
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
			int nbrPages=nbrComputer/Page.getNbrElements();
			if(nbrComputer%Page.getNbrElements()!=0) {
				nbrPages++;
			}
			Page.setNbrPages(nbrPages);
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
