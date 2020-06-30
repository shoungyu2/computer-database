package com.excilys.cdb.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("ListComputerController")
public class ListComputerController {
		
	@Autowired
	private ComputerService computerService;

	private List<Computer> getPcList(String filter, String search, String order, Page page){
		
		return computerService.getComputersService(search, filter,  order, page);
		
	}
	
	@GetMapping
	public String getListComputersController( ModelMap modelMap,
			@RequestParam(name="search", required=false, defaultValue="")String search,
			@RequestParam(name="filter", required=false, defaultValue="")String filter,
			@RequestParam(name="order", required=false, defaultValue="")String order,
			@RequestParam(name="nbrElement", required=false, defaultValue="")String nbrElement,
			@RequestParam(name="currentPage", required=false, defaultValue="")String currentPage) {
				
		Page page=MethodServlet.setNumPage(modelMap, currentPage);
		MethodServlet.setNbrElementsInPage(nbrElement);
		int nbrComputer=MethodServlet.setNbrComputer(computerService, search);
		MethodServlet.setNbrPages(nbrComputer);
		List<Computer> pcList=getPcList(filter, search, order, page);
		
		modelMap.addAttribute("search", search);
		modelMap.addAttribute("filter", filter);
		modelMap.addAttribute("order", order);
		modelMap.addAttribute("nbrElement", nbrElement);
		modelMap.addAttribute("pcCount", nbrComputer);
		modelMap.addAttribute("pcList", pcList);
		modelMap.addAttribute("nbrPage", Page.getNbrPages());
		
		return "dashboard";
		
	}
	
	@PostMapping
	public String postListComputerController(ModelMap modelMap,
			@RequestParam(name="success", required=false, defaultValue="") String success,
			@RequestParam(name="currentPage", required=false, defaultValue="") String currentPage) {
		
		modelMap.addAttribute("success", success);
		
		return getListComputersController(modelMap, "", "", "", "20", currentPage);
		
	}
	
}
