package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.Mapper;
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
	private Mapper mapper;
		
	@GetMapping
	public String getAddComputerController(ModelMap modelMap) {
				
		List<Company> listComp=companyService.listCompanyService();
		modelMap.addAttribute("listCompany", listComp);
		
		return "addComputer";
		
	}
	
	@PostMapping
	public String postAddComputerController(ModelMap modelMap,
			@RequestParam(name="companyId", required=false, defaultValue="") String companyId,
			@RequestParam(name="computerName", required=true) String computerName,
			@RequestParam(name="introduced", required=false, defaultValue="") String introduced,
			@RequestParam(name="discontinued", required=false, defaultValue="") String discontinued) {
				
		CompanyDTO companyDTO=ControllerUtil.getCompanyDTOFromRequest(modelMap, companyService, mapper, companyId);
		ComputerDTO computerDTO= new ComputerDTO.Builder("-1", computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO).build();
		
		if(!(ControllerUtil.createOrUpdateComputer(modelMap, computerService, computerDTO, true))) {
			return "redirect:ErrorServlet";
		}
		
		return getAddComputerController(modelMap);
		
	}
	
}
