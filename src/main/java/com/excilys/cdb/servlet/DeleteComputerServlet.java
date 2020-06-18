package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.AllServices;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -5703698584643227969L;
	private static final Logger LOGGER=org.apache.log4j.Logger.getLogger(DeleteComputerServlet.class);
	static {
		try {
			FileAppender fa= new FileAppender(new PatternLayout("%d [%p] %m%n"), 
					"src/main/java/com/excilys/cdb/logger/log.txt");
			LOGGER.addAppender(fa);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void deleteListComputer(HttpServletRequest request, AllServices allServices, String[] idSelected) {
		
		String messageLogger="";
		for(String id:idSelected) {
			try {
				allServices.getComputerService().deleteComputerService(id);
			} catch(InvalidEntryException ie) {
				String errorMessage="";
				for(Problems pb:ie.getListProb()) {
					errorMessage+=pb.toString()+"\n";
				}
				LOGGER.error(ie.getStackTrace());
				request.setAttribute("errors", errorMessage);
			}
			messageLogger+=id+",";
		}
		
		LOGGER.info("Commputers deleted from database: "+messageLogger);
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AllServices allServices=MethodServlet.getAllServices(request);
		String selected=request.getParameter("selection");
		String[] idSelected=selected.split(",");
		
		deleteListComputer(request, allServices, idSelected);
		
		doGet(request, response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AllServices allServices=MethodServlet.getAllServices(request);
		ComputerService computerService= allServices.getComputerService();

		Page page=MethodServlet.setNumPage(request);
		MethodServlet.setNbrElementsInPage(request);
		int nbrComputer= MethodServlet.setNbrComputer(computerService, null);
		MethodServlet.setNbrPages(nbrComputer);
		
		request.setAttribute("pcCount", nbrComputer);
		request.setAttribute("pcList", computerService.listComputerService(page));
		request.setAttribute("nbrPage", Page.getNbrPages());
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		
	}
	
}
