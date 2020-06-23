package com.excilys.cdb.servlet;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class MethodServlet {

	private static final Logger LOGGER=Logger.getLogger(MethodServlet.class);
		
	public static CompanyDTO getCompanyDTOFromRequest(HttpServletRequest request, CompanyService companyService, Mapper map) {
		
		String companyId=request.getParameter("companyId");
		Optional<Company> oComp=Optional.empty();
		
		try {
			oComp=companyService.showDetailCompanyService(companyId);
		} catch (InvalidEntryException e1) {
			String errorMessage="";
			for(Problems pb : e1.getListProb()) {
				errorMessage+=pb.toString()+"\n";
			}
			request.setAttribute("errors", errorMessage);
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
	
	public static void setNbrElementsInPage(HttpServletRequest request) {
		
		String nbrElementStr=request.getParameter("nbrElement");
		if(nbrElementStr!=null) {
			int nbrElement=Integer.parseInt(nbrElementStr);
			Page.setNbrElements(nbrElement);
		}
		
	}
	
	public static Page setNumPage(HttpServletRequest request) {
		
		Page page;
		String numPageStr=request.getParameter("currentPage");
		
		if(numPageStr!=null) {
			int numPage=Integer.parseInt(numPageStr);
			page=new Page(numPage);
			request.setAttribute("numPage", numPage);
		}
		else {
			page=new Page(1);
			request.setAttribute("numPage", 1);
		}
		
		return page;
		
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
	public static boolean createOrUpdateComputer(HttpServletRequest request, ComputerService computerService, ComputerDTO computerDTO, boolean action) {
	
		try {
			if(action) {
				boolean success=computerService.createComputerService(computerDTO);
				if(success) {
					LOGGER.info("Computer added in the database:\n"+computerDTO.toString());
					request.setAttribute("success", "Computer successfully added");
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
					request.setAttribute("success", "Computer successfully edited");
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
			request.setAttribute("errors", errorMessage);
			return true;
		} catch (ComputerIsNullException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getStackTrace());
			e.printStackTrace();
			return true;
		}
		
	}
}
