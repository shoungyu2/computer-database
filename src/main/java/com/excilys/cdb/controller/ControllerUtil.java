package com.excilys.cdb.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadRequestException;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class ControllerUtil {

	private static final Logger LOGGER=Logger.getLogger(ControllerUtil.class);
		
	public static CompanyDTO getCompanyDTOFromRequest(ModelMap modelMap, CompanyService companyService, Mapper map, String companyId) {
		
		Optional<Company> oComp=Optional.empty();
		
		try {
			oComp=companyService.showDetailCompanyService(companyId);
		} catch (InvalidEntryException e1) {
			String errorMessage="";
			for(Problems pb : e1.getListProb()) {
				errorMessage+=pb.toString()+"\n";
			}
			modelMap.addAttribute("errors", errorMessage);
		}
		
		CompanyDTO companyDTO=null;
		if (oComp.isPresent()) {
			companyDTO=map.companyToString(oComp.get());
		}
		
		return companyDTO;
		
	}
	
	public static ComputerDTO getComputerDTOFromRequest(HttpServletRequest request, String id, CompanyDTO companyDTO) {
		
		String computerName=request.getParameter("computerName");
		String introduced=request.getParameter("introduced");
		String discontinued=request.getParameter("discontinued");
								
		return new ComputerDTO.Builder(id, computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO)
				.build();	
		
	}
	
	public static void setNbrElementsInPage(String nbrElementStr) throws BadRequestException{
		
		if(nbrElementStr!=null && !nbrElementStr.isEmpty()) {
			try {
				int nbrElement=Integer.parseInt(nbrElementStr);
				Page.setNbrElements(nbrElement);
			} catch (NumberFormatException nfe) {
				throw new BadRequestException();
			}
		}
		
	}
	
	public static Page setNumPage(ModelMap modelMap, String numPageStr) throws BadRequestException{
		
		Page page;
		
		if(numPageStr!=null && !numPageStr.isEmpty()) {
			try {
				int numPage=Integer.parseInt(numPageStr);
				page=new Page(numPage);
				modelMap.addAttribute("numPage", numPage);
			} catch (NumberFormatException nfe) {
				throw new BadRequestException();
			}
		}
		else {
			page=new Page(1);
			modelMap.addAttribute("numPage", 1);
		}
		
		return page;
		
	}
	
	public static void verifNumPage(Page page, int nbrPage) throws BadRequestException{
		
		if(page.getNumPage()<=0 || page.getNumPage()>nbrPage) {
			throw new BadRequestException();
		}
		
	}
	
	public static void setNbrPages(int nbrComputer) {
		
		int nbrPages= nbrComputer/Page.getNbrElements();
		if(nbrComputer%Page.getNbrElements()!=0) {
			nbrPages++;
		}
		Page.setNbrPages(nbrPages);
	
	}
	
	public static int setNbrComputer(ComputerService computerService, String search) {
		
			return computerService.getNbrComputerService(search);
			
	}
	
	/**
	 * Fonction permettant la création ou la mise à jour d'un PC
	 * @param request la requête HTTP qui sera renvoyée
	 * @param serviceContainer 
	 * @param computerDTO les informations de l'ordinateur à créer/mettre à jour
	 * @param action true->créer un ordinateur/false->mettre à jour un ordinateur
	 * @return un booléen indiquant si l'action a entraîné un problème dans la bdd ou non
	 */
	public static boolean createOrUpdateComputer(ModelMap modelMap, ComputerService computerService, ComputerDTO computerDTO, boolean action) {
	
		try {
			if(action) {
				boolean success=computerService.createComputerService(computerDTO);
				if(success) {
					LOGGER.info("Computer added in the database:\n"+computerDTO.toString());
					modelMap.addAttribute("success", "Computer successfully added");
					return true;
				}
				else {
					return false;
				}
			}
			else {
				boolean success=computerService.updateComputerService(computerDTO);
				if(success) {
					LOGGER.info("Computer updated in the database:\n"+computerDTO.toString());
					modelMap.addAttribute("success", "Computer successfully edited");
					return true;
				}
				else {
					return false;
				}
			}
		} catch (InvalidEntryException e) {
			List<Problems> listProbs=e.getListProb();
			String errorMessage="";
			for(Problems pb:listProbs) {
				errorMessage+=pb.toString()+"\n";
			}
			LOGGER.error(errorMessage,e);
			modelMap.addAttribute("errors", errorMessage);
			return true;
		} catch (ComputerIsNullException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getStackTrace());
			e.printStackTrace();
			return true;
		}
		
	}
}
