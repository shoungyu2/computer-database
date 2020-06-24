package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.spring.SpringConfiguration;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/ListComputerServlet")
public class ListComputerServlet extends HttpServlet{

	private static final long serialVersionUID = -3655410377159367069L;
	
	private static AnnotationConfigApplicationContext context=SpringConfiguration.getContext();
	
	private ComputerService computerService=context.getBean(ComputerService.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setAttribute("success", request.getParameter("success"));
		
		doGet(request,response);
		
	}
	
	private List<Computer> getPcList(String filter, String search, String order, Page page){
		
		return computerService.getComputersService(search, filter,  order, page);
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String search=request.getParameter("search");
		String order=request.getParameter("order");
		String direction=request.getParameter("direction");
		
		Page page=MethodServlet.setNumPage(request);
		MethodServlet.setNbrElementsInPage(request);
		int nbrComputer=MethodServlet.setNbrComputer(computerService, search);
		MethodServlet.setNbrPages(nbrComputer);
		List<Computer> pcList=getPcList(order, search, direction, page);
			
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
