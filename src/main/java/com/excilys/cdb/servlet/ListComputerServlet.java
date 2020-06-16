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

import com.excilys.cdb.model.Computer;
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
	
	private Page setNumPage(HttpServletRequest request) {
		
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
		
		return page;
		
	}
	
	private void setNbrElementsInPage(HttpServletRequest request) {
		
		String nbrElementStr=request.getParameter("nbrElement");
		if(nbrElementStr!=null) {
			int nbrElement=Integer.parseInt(nbrElementStr);
			Page.setNbrElements(nbrElement);
		}
		
	}
	
	private AllServices getAllServices(HttpServletRequest request) throws ServletException {
		
		ServletContext sc= getServletContext();
		Object obj=sc.getAttribute("AllServices");
		if(obj instanceof AllServices) {
			return (AllServices) obj;
		}
		else {
			throw new ServletException("Bad context: the attribute \"AllServices\" is wrong");
		}
		
	}
	
	private int setNbrComputer(ComputerService computerService, String search) {
		
		if(search!=null && !search.isEmpty()) {
			return computerService.getNbrComputerInSearchService(search);			
		}
		else {
			return computerService.getNbrComputerService();
		}
		
	}
	
	private void setNbrPages(int nbrComputer) {
		
		int nbrPages= nbrComputer/Page.getNbrElements();
		if(nbrComputer%Page.getNbrElements()!=0) {
			nbrPages++;
		}
		Page.setNbrPages(nbrPages);
	}
	
	private List<Computer> getPcList(ComputerService computerService, String order, String search, String direction, Page page){
		
		if(order!=null && !order.isEmpty()) {
			return computerService.orderByService(order, search, direction, page);
		}
		else {
			return computerService.searchComputerService(search, page);
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String search=request.getParameter("search");
		String order=request.getParameter("order");
		String direction=request.getParameter("direction");
		
		Page page=setNumPage(request);
		setNbrElementsInPage(request);
		AllServices allServices=getAllServices(request);
		ComputerService computerService= allServices.getComputerService();
		int nbrComputer=setNbrComputer(computerService, search);
		setNbrPages(nbrComputer);
		List<Computer> pcList=getPcList(computerService, order, search, direction, page);
			
		request.setAttribute("search", search);
		request.setAttribute("order", order);
		request.setAttribute("direction", direction);
		request.setAttribute("pcCount", nbrComputer);
		request.setAttribute("pcList", pcList);
		request.setAttribute("nbrPage", Page.getNbrPages());		
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
	
	}
	
}
