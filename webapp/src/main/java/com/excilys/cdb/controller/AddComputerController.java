package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.validator.ComputerDTOValidator;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("AddComputerController")
public class AddComputerController{
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
		
	@Autowired
	private ComputerDTOValidator computerDTOValidator;
	
	@GetMapping
	public String getAddComputerController(ModelMap modelMap) {
			
		List<Company> listComp=companyService.listCompanyService();
		modelMap.addAttribute("listCompany", listComp);
		modelMap.addAttribute("computerDTO", new ComputerDTO.Builder("", ""));
		
		return "addComputer";
		
	}
	
	@PostMapping
	public String postAddComputerController(ModelMap modelMap, ComputerDTO.Builder computerDTOBuilder, BindingResult br) {

		ComputerDTO computerDTO= computerDTOBuilder.build();
		modelMap.addAttribute("computerDTO", computerDTOBuilder);
		
		computerDTOValidator.validate(computerDTOBuilder, br);
		if(br.hasErrors()) {
			return "addComputer";
		}
		
		if(!(ControllerUtil.createOrUpdateComputer(modelMap, computerService, computerDTO, true))) {
			return "500";
		}
		
		return getAddComputerController(modelMap);
		
	}
	
}
