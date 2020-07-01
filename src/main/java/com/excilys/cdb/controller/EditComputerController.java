package com.excilys.cdb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("EditComputerController")
public class EditComputerController{
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private Mapper mapper;	
	
	private void setAllAttribute(ModelMap modelMap, String numPage, String nbrPage) {
		
		modelMap.addAttribute("numPage", numPage);
		modelMap.addAttribute("nbrPage", nbrPage);
		modelMap.addAttribute("listCompany", companyService.listCompanyService());
		
	}
	
	private void setComputerInRequest(ModelMap modelMap, Computer computer) {
		
		if(computer!=null) {
			modelMap.addAttribute("computerId", computer.getId());
			modelMap.addAttribute("computerName", computer.getName());
			modelMap.addAttribute("introducedDate",mapper.dateToString(computer.getIntroductDate()));
			modelMap.addAttribute("discontinuedDate",mapper.dateToString(computer.getDiscontinueDate()));
			CompanyDTO comp= mapper.companyToString(computer.getCompany());
			if(comp!=null) {
				modelMap.addAttribute("companyId", comp.getId());
			}
		}
		
	}

	
	@GetMapping
	public String getEditComputerController(ModelMap modelMap,
			@RequestParam(name="computerId", required=true) String computerId,
			@RequestParam(name="numPage", required=false, defaultValue="1") String numPage,
			@RequestParam(name="nbrPage", required=true) String nbrPage) {
				
		setAllAttribute(modelMap, numPage, nbrPage);
		try {
			Optional<Computer> oComp=computerService.getComputerFromIdService(computerId);
			Computer c=oComp.isEmpty()?null:oComp.get();
			setComputerInRequest(modelMap, c);
		} catch(InvalidEntryException iee) {
			iee.printStackTrace();
		}
		
		return "editComputer";
		
	}
	
	@PostMapping
	public String postEditComputerController(ModelMap modelMap,
			@RequestParam(name="companyId", required=false, defaultValue="") String companyId,
			@RequestParam(name="computerId", required=true) String computerId,
			@RequestParam(name="computerName", required=true) String computerName,
			@RequestParam(name="introduced", required=false, defaultValue="") String introduced,
			@RequestParam(name="discontinued", required=false, defaultValue="") String discontinued,
			@RequestParam(name="numPage", required=true) String numPageStr,
			@RequestParam(name="nbrPage", required=true) String nbrPageStr) {
				
		CompanyDTO companyDTO = ControllerUtil.getCompanyDTOFromRequest(modelMap, companyService, mapper, companyId);
		ComputerDTO computerDTO = new ComputerDTO.Builder(computerId, computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO).build();
		
		if(ControllerUtil.createOrUpdateComputer(modelMap, computerService, computerDTO, false)) {
			int numPage= Integer.parseInt(numPageStr);
			int nbrPage= Integer.parseInt(nbrPageStr);
			
			modelMap.addAttribute("numPage", numPage);
			modelMap.addAttribute("nbrPage", nbrPage);
		}
		else {
			return "500";
		}
		
		return getEditComputerController(modelMap, computerId, numPageStr, nbrPageStr);
		
	}
	
}
