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
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.service.CompanieService;
import com.excilys.cdb.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

	@Mock
	CompanieDAO companyDAO;
	
	@Mock
	Mapper map;
	
	@Mock
	VerificationService verifService;
	
	CompanieService companyService= new CompanieService();
	
	@Test
	public void listCompanyServiceTest() {
		
		companyService.setCompDAO(companyDAO);
		
		Page page= new Page(1);
		
		Mockito.when(companyDAO.listCompanie(page)).thenReturn(new ArrayList<Companie>());
		
		assertEquals(new ArrayList<Companie>(), companyService.listCompanieService(page));
		
	}

	@Test
	public void showDetailCompanyServiceTest() throws InvalidEntryException, NotFoundException {
		
		companyService.setCompDAO(companyDAO);
		companyService.setVerifServ(verifService);
		companyService.setMap(map);
		
		Companie comp=new Companie("343 Industries", 343);
		Optional<Companie> oComp= Optional.of(comp);
		
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDCompanieInBDD(0);
		Mockito.when(map.stringToID("blablou")).thenThrow(new NumberFormatException());
		Mockito.when(map.stringToID("-1")).thenReturn(-1);
		Mockito.when(map.stringToID("343")).thenReturn(343);
		Mockito.when(companyDAO.showDetailCompanie(343)).thenReturn(oComp);
		
		try {
			companyService.showDetailCompanieService("blablou");
			assert(false);
		} catch (InvalidEntryException iee) {
			assert(true);
		}
		
		try {
			companyService.showDetailCompanieService("0");
			assert(false);
		} catch(InvalidEntryException iee) {
			assert(true);
		}
		
		assertEquals(Optional.empty(), companyService.showDetailCompanieService("-1"));
		assertEquals(oComp, companyService.showDetailCompanieService("343"));
		
	}
	
}
