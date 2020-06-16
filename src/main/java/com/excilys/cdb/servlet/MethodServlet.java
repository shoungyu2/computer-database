package com.excilys.cdb.servlet;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.AllServices;

public class MethodServlet {

	public static AllServices getAllServices(HttpServletRequest request) throws ServerException {
		
		ServletContext context=request.getServletContext();
		Object obj= context.getAttribute("AllServices");
		if (obj instanceof AllServices) {
			return (AllServices) obj;
		}
		else {
			throw new ServerException("Bad context, the attribute \"AllServices\" is wrong");
		}
	}
	
	public static CompanyDTO getCompanyDTOFromRequest(HttpServletRequest request, AllServices allServices) {
		
		String companyId=request.getParameter("companyId");
		Optional<Company> oComp=Optional.empty();
		
		try {
			oComp=allServices.getCompanyService().showDetailCompanyService(companyId);
		} catch (InvalidEntryException e1) {
			String errorMessage="";
			for(Problems pb : e1.getListProb()) {
				errorMessage+=pb.toString()+"\n";
			}
			request.setAttribute("errors", errorMessage);
		}
		
		CompanyDTO companyDTO=null;
		if (oComp.isPresent()) {
			companyDTO=allServices.getMap().companyToString(oComp.get());
		}
		
		return companyDTO;
		
	}
	
	public static ComputerDTO getComputerDTOFromRequest(HttpServletRequest request, String id, CompanyDTO companyDTO) {
		
		String computerName=request.getParameter("computerName");
		String introduced=request.getParameter("introduced");
		String discontinued=request.getParameter("discontinued");
								
		return new ComputerDTO.ComputerDTOBuilder(id, computerName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyDTO(companyDTO)
				.build();	
		
	}
	
	public static void createOrUpdateComputer(HttpServletRequest request, AllServices allServices, ComputerDTO computerDTO, boolean action) {
	
		try {
			if(action) {
				allServices.getComputerService().createComputerService(computerDTO);
				request.setAttribute("success", "Computer successfully added");
			}
			else {
				allServices.getComputerService().updateComputerService(computerDTO);
				request.setAttribute("success", "Computer successfully edited");
			}
		} catch (InvalidEntryException e) {
			List<Problems> listProbs=e.getListProb();
			String errorMessage="";
			for(Problems pb:listProbs) {
				errorMessage+=pb.toString()+"\n";
			}
			request.setAttribute("errors", errorMessage);
		} catch (ComputerIsNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
