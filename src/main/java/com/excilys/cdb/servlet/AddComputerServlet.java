package com.excilys.cdb.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class AddComputerServlet{
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private Mapper mapper;
	
	@RequestMapping("/AddComputerServlet")
	
	@GetMapping
	public ModelAndView getAddComputerController() {
		
		ModelAndView modelAndView = new ModelAndView("redirect:addComputer");
		
		List<Company> listComp=companyService.listCompanyService();
		modelAndView.addObject("listCompany", listComp);
		
		return modelAndView;
		
	}
	
	@PostMapping
	public ModelAndView postAddComputerController(
			@RequestParam(name="companyID", required=false, defaultValue="") String companyId,
			@RequestParam(name="computerName", required=true) String computerName,
			@RequestParam(name="introduced", required=false, defaultValue="") String introduced,
			@RequestParam(name="discontinued", required=false, defaultValue="") String discontinued) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:AddComputerServlet");
		
		CompanyDTO companyDTO=MethodServlet.getCompanyDTOFromRequest(modelAndView, companyService, mapper, companyId);
		ComputerDTO computerDTO= new ComputerDTO.Builder("-1", computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO).build();
		
		if(!(MethodServlet.createOrUpdateComputer(modelAndView, computerService, computerDTO, true))) {
			modelAndView.setViewName("redirect:ErrorServlet");
		}
		
		return modelAndView;
		
	}
	
}
