package com.excilys.cdb.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ListComputerController {
		
	@Autowired
	private ComputerService computerService;

	@RequestMapping("/ListComputerServlet")
	
	private List<Computer> getPcList(String filter, String search, String order, Page page){
		
		return computerService.getComputersService(search, filter,  order, page);
		
	}
	
	@GetMapping
	public ModelAndView getListComputersController(
			@RequestParam(name="search", required=false, defaultValue="")String search,
			@RequestParam(name="filter", required=false, defaultValue="")String filter,
			@RequestParam(name="order", required=false, defaultValue="")String order,
			@RequestParam(name="nbrElement", required=false, defaultValue="")String nbrElement,
			@RequestParam(name="currentPage", required=false, defaultValue="")String currentPage) {
		
		ModelAndView modelAndView= new ModelAndView("redirect:dashboard");
		
		Page page=MethodServlet.setNumPage(modelAndView, currentPage);
		MethodServlet.setNbrElementsInPage(nbrElement);
		int nbrComputer=MethodServlet.setNbrComputer(computerService, search);
		MethodServlet.setNbrPages(nbrComputer);
		List<Computer> pcList=getPcList(filter, search, order, page);
		
		modelAndView.addObject("search", search);
		modelAndView.addObject("filter", filter);
		modelAndView.addObject("order", order);
		modelAndView.addObject("pcCount", nbrComputer);
		modelAndView.addObject("pcList", pcList);
		modelAndView.addObject("nbrPage", Page.getNbrPages());
		
		return modelAndView;
		
	}
	
	@PostMapping
	public ModelAndView postListComputerController(
			@RequestParam(name="success", required=false, defaultValue="")String success) {
		
		ModelAndView modelAndView= new ModelAndView("redirect:ListComputerServlet");
		
		modelAndView.addObject("success", success);
		
		return modelAndView;
		
	}
	
}
