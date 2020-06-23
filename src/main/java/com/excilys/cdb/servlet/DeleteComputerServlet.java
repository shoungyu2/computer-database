package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.spring.SpringConfiguration;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = -5703698584643227969L;
	private static final Logger LOGGER=org.apache.log4j.Logger.getLogger(DeleteComputerServlet.class);
	
	private static AnnotationConfigApplicationContext context=SpringConfiguration.getContext();
	
	private ComputerService computerService=context.getBean(ComputerService.class);
	
	private boolean deleteListComputer(HttpServletRequest request, String[] idSelected) {
		
		String messageLogger="";
		for(String id:idSelected) {
			try {
				if(!computerService.deleteComputerService(id)) {
					return false;
				}
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
		return true;
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String selected=request.getParameter("selection");
		String[] idSelected=selected.split(",");
		
		if(deleteListComputer(request, idSelected)) {
			doGet(request, response);
		}
		else {
			RequestDispatcher rd=request.getRequestDispatcher("/ErrorServlet");
			rd.forward(request, response);
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Page page=MethodServlet.setNumPage(request);
		MethodServlet.setNbrElementsInPage(request);
		int nbrComputer= MethodServlet.setNbrComputer(computerService, null);
		MethodServlet.setNbrPages(nbrComputer);
		
		request.setAttribute("pcCount", nbrComputer);
		request.setAttribute("pcList", computerService.getComputersService("", "", "", page));
		request.setAttribute("nbrPage", Page.getNbrPages());
		
		RequestDispatcher rd= request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		
	}
	
}
