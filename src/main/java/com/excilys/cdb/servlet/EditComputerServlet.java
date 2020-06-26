package com.excilys.cdb.servlet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class EditComputerServlet{
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private Mapper mapper;
	
	@RequestMapping("/EditComputerServlet")
	
	
	private void setAllAttribute(ModelAndView modelAndView, String numPage, String nbrPage) {
		
		modelAndView.addObject("numPage", numPage);
		modelAndView.addObject("nbrPage", nbrPage);
		modelAndView.addObject("listCompany", companyService.listCompanyService());
		
	}
	
	private void setComputerInRequest(ModelAndView modelAndView, Computer computer) {
		
		if(computer!=null) {
			modelAndView.addObject("computerId", computer.getId());
			modelAndView.addObject("computerName", computer.getName());
			modelAndView.addObject("introducedDate",mapper.dateToString(computer.getIntroductDate()));
			modelAndView.addObject("discontinuedDate",mapper.dateToString(computer.getDiscontinueDate()));
			CompanyDTO comp= mapper.companyToString(computer.getCompany());
			if(comp!=null) {
				modelAndView.addObject("companyId", comp.getId());
			}
		}
		
	}

	
	@GetMapping
	public ModelAndView getEditComputerController(
			@RequestParam(name="computerId", required=true) String computerId,
			@RequestParam(name="numPage", required=false, defaultValue="1") String numPage,
			@RequestParam(name="nbrPage", required=true) String nbrPage) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:editComputer");
		
		setAllAttribute(modelAndView, numPage, nbrPage);
		try {
			Optional<Computer> oComp=computerService.getComputerFromIdService(computerId);
			Computer c=oComp.isEmpty()?null:oComp.get();
			setComputerInRequest(modelAndView, c);
		} catch(InvalidEntryException iee) {
			iee.printStackTrace();
		}
		
		return modelAndView;
		
	}
	
	@PostMapping
	public ModelAndView postEditComputerController(
			@RequestParam(name="companyId", required=false, defaultValue="") String companyId,
			@RequestParam(name="computerId", required=true) String computerId,
			@RequestParam(name="computerName", required=true) String computerName,
			@RequestParam(name="introduced", required=false, defaultValue="") String introduced,
			@RequestParam(name="discontinued", required=false, defaultValue="") String discontinued,
			@RequestParam(name="numPage", required=true) String numPageStr,
			@RequestParam(name="nbrPage", required=true) String nbrPageStr) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:EditComputerServlet");
		
		CompanyDTO companyDTO = MethodServlet.getCompanyDTOFromRequest(modelAndView, companyService, mapper, companyId);
		ComputerDTO computerDTO = new ComputerDTO.Builder(computerId, computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO).build();
		
		if(MethodServlet.createOrUpdateComputer(modelAndView, computerService, computerDTO, false)) {
			int numPage= Integer.parseInt(numPageStr);
			int nbrPage= Integer.parseInt(nbrPageStr);
			
			modelAndView.addObject("numPage", numPage);
			modelAndView.addObject("nbrPage", nbrPage);
		}
		else {
			modelAndView.setViewName("redirect:ErrorServlet");
		}
		
		return modelAndView;
		
	}
	
}
