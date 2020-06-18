package com.excilys.cdb.service_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

	@Mock
	CompanyDAO companyDAO;
	
	@Mock
	Mapper map;
	
	@Mock
	VerificationService verifService;
	
	CompanyService companyService= new CompanyService();
	
	@Test
	public void listCompanyServiceTest() {
		
		companyService.setCompDAO(companyDAO);
				
		Mockito.when(companyDAO.listCompany()).thenReturn(new ArrayList<Company>());
		
		assertEquals(new ArrayList<Company>(), companyService.listCompanyService());
		
	}

	@Test
	public void showDetailCompanyServiceTest() throws InvalidEntryException, NotFoundException {
		
		companyService.setCompDAO(companyDAO);
		companyService.setVerifServ(verifService);
		companyService.setMap(map);
		
		Company comp=new Company("343 Industries", 343);
		Optional<Company> oComp= Optional.of(comp);
		
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDCompanieInBDD(0);
		Mockito.when(map.stringToID("blablou")).thenThrow(new NumberFormatException());
		Mockito.when(map.stringToID("-1")).thenReturn(-1);
		Mockito.when(map.stringToID("343")).thenReturn(343);
		Mockito.when(companyDAO.showDetailCompany(343)).thenReturn(oComp);
		
		try {
			companyService.showDetailCompanyService("blablou");
			assert(false);
		} catch (InvalidEntryException iee) {
			assert(true);
		}
		
		try {
			companyService.showDetailCompanyService("0");
			assert(false);
		} catch(InvalidEntryException iee) {
			assert(true);
		}
		
		assertEquals(Optional.empty(), companyService.showDetailCompanyService("-1"));
		assertEquals(oComp, companyService.showDetailCompanyService("343"));
		
	}
	
	@Test
	public void deleteCompanyService() throws NotFoundException {
		
		companyService.setCompDAO(companyDAO);
		companyService.setVerifServ(verifService);
		companyService.setMap(map);
		
		Mockito.when(map.stringToID("blablou")).thenThrow(new NumberFormatException());
		Mockito.when(map.stringToID("-1")).thenReturn(-1);
		Mockito.when(map.stringToID("1")).thenReturn(1);
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDCompanieInBDD(-1);
		Mockito.doNothing().when(verifService).verifIDCompanieInBDD(1);
		Mockito.doNothing().when(companyDAO).deleteCompany(1);
		
		try {
			companyService.deleteCompanyService("1");
			assert(true);
			companyService.deleteCompanyService("blablou");
			assert(false);
		} catch(InvalidEntryException iee) {
			assertEquals(1, iee.getListProb().size());
		}
		
		try {
			companyService.deleteCompanyService("-1");
			assert(false);
		} catch(InvalidEntryException iee) {
			assertEquals(1, iee.getListProb().size());
		}
		
	}
	
}
