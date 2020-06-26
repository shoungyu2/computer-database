package com.excilys.cdb.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
public class DeleteComputerServlet{

	private static final Logger LOGGER=org.apache.log4j.Logger.getLogger(DeleteComputerServlet.class);
		
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping("/DeleteComputerServlet")
	
	private boolean deleteListComputer(ModelAndView modelAndView, String[] idSelected) {
		
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
				modelAndView.addObject("errors", errorMessage);
			}
			messageLogger+=id+",";
		}
		
		LOGGER.info("Commputers deleted from database: "+messageLogger);
		return true;
		
	}
	
	@GetMapping
	public ModelAndView getDeleteComputerController(
			@RequestParam(name="currentPage", required=false, defaultValue="") String numPage,
			@RequestParam(name="nbrElement", required=false, defaultValue="") String nbrElement) {
		
		ModelAndView modelAndView= new ModelAndView("redirect:dashboard");
		
		Page page=MethodServlet.setNumPage(modelAndView, numPage);
		MethodServlet.setNbrElementsInPage(nbrElement);
		int nbrComputer=MethodServlet.setNbrComputer(computerService, null);
		MethodServlet.setNbrPages(nbrComputer);
		
		modelAndView.addObject("pcCount", nbrComputer);
		modelAndView.addObject("pcList", computerService.getComputersService("", "", "", page));
		modelAndView.addObject("nbrPage", Page.getNbrPages());
		
		return modelAndView;
		
	}
	
	@PostMapping
	public ModelAndView postDeleteComputerController(
			@RequestParam(name="selection", required=false, defaultValue="") String selection) {
		
		ModelAndView modelAndView= new ModelAndView("redirect:DeleteComputerServlet");
		
		String[] selected=selection.split(",");
		if(!(deleteListComputer(modelAndView, selected))) {
			modelAndView.setViewName("redirect:ErrorServlet");
		}
		
		return modelAndView;
		
	}

}
