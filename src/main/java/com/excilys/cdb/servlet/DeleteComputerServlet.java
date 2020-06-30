package com.excilys.cdb.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("DeleteComputerController")
public class DeleteComputerServlet{

	private static final Logger LOGGER=org.apache.log4j.Logger.getLogger(DeleteComputerServlet.class);
		
	@Autowired
	private ComputerService computerService;
		
	private boolean deleteListComputer(ModelMap modelMap, String[] idSelected) {
		
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
				modelMap.addAttribute("errors", errorMessage);
			}
			messageLogger+=id+",";
		}
		
		LOGGER.info("Commputers deleted from database: "+messageLogger);
		return true;
		
	}
	
	@GetMapping
	public String getDeleteComputerController(ModelMap modelMap,
			@RequestParam(name="currentPage", required=false, defaultValue="") String numPage,
			@RequestParam(name="nbrElement", required=false, defaultValue="") String nbrElement) {
				
		Page page=MethodServlet.setNumPage(modelMap, numPage);
		MethodServlet.setNbrElementsInPage(nbrElement);
		int nbrComputer=MethodServlet.setNbrComputer(computerService, null);
		MethodServlet.setNbrPages(nbrComputer);
		
		modelMap.addAttribute("pcCount", nbrComputer);
		modelMap.addAttribute("pcList", computerService.getComputersService("", "", "", page));
		modelMap.addAttribute("nbrPage", Page.getNbrPages());
		
		return "dashboard";
		
	}
	
	@PostMapping
	public String postDeleteComputerController(ModelMap modelMap,
			@RequestParam(name="selection", required=false, defaultValue="") String selection) {
				
		String[] selected=selection.split(",");
		if(!(deleteListComputer(modelMap, selected))) {
			return "redirect:ErrorServlet";
		}
		
		return getDeleteComputerController(modelMap, "", "");
		
	}

}
