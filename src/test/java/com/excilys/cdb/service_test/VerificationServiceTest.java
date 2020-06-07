package com.excilys.cdb.service_test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class VerificationServiceTest {

	@Mock
	ComputerDAO computerDAO;
	
	@Mock
	CompanieDAO companyDAO;
	
	VerificationService verifService=new VerificationService();
	
	@Test
	public void verifDateTest() {
		
		List<Problems> listProbs1=new ArrayList<Problems>();
		List<Problems> listProbs2=new ArrayList<Problems>();
		List<Problems> listProbs3=new ArrayList<Problems>();
		List<Problems> listProbs4=new ArrayList<Problems>();
		
		
		LocalDateTime ldt1=LocalDateTime.parse("2002-02-02T00:00:00");
		LocalDateTime ldt2=LocalDateTime.parse("2003-03-03T00:00:00");
		
		verifService.verifDate(ldt1, ldt2, listProbs1);
		verifService.verifDate(ldt2, ldt1, listProbs2);
		verifService.verifDate(ldt1, null, listProbs3);
		verifService.verifDate(null, ldt2, listProbs4);

		assertEquals(0, listProbs1.size());
		assertEquals(1, listProbs2.size());
		assertEquals(0, listProbs3.size());
		assertEquals(0, listProbs4.size());
		
	}
	
	@Test
	public void verifNameIsNotNullTest() {
		
		List<Problems> listProbs1=new ArrayList<Problems>();
		List<Problems> listProbs2=new ArrayList<Problems>();
		List<Problems> listProbs3=new ArrayList<Problems>();
		
		String name="fjqsfjq";
		
		verifService.verifNameIsNotNull(name, listProbs1);
		verifService.verifNameIsNotNull("", listProbs2);
		verifService.verifNameIsNotNull(null, listProbs3);

		assertEquals(0, listProbs1.size());
		assertEquals(1, listProbs2.size());
		assertEquals(1, listProbs3.size());
		
	}
	
	@Test
	public void verifComputerIdInBDDTest() {
		
		verifService.setComputerDAO(computerDAO);
		
		Optional<Computer> empty=Optional.empty();
		Computer comp= new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setEntreprise(null)
				.build();
		Optional<Computer> oComp= Optional.of(comp);
		
		Mockito.when(computerDAO.showDetailComputer(0)).thenReturn(empty);
		Mockito.when(computerDAO.showDetailComputer(343)).thenReturn(oComp);
		
		try {
			verifService.verifIDComputerInBDD(343);
			assert(true);
			verifService.verifIDComputerInBDD(0);
			assert(false);
		} catch (NotFoundException nfe) {
			assert(true);
		}
		
	}

	@Test
	public void verifCompanyIdInBDDTest() {
		
		verifService.setCompanieDAO(companyDAO);
		
		Optional<Companie> empty=Optional.empty();
		Companie comp= new Companie("343 Industries", 343);
		Optional<Companie> oComp=Optional.of(comp);
		
		Mockito.when(companyDAO.showDetailCompanie(0)).thenReturn(empty);
		Mockito.when(companyDAO.showDetailCompanie(343)).thenReturn(oComp);
		
		try {
			verifService.verifIDCompanieInBDD(343);
			assert(true);
			verifService.verifIDCompanieInBDD(0);
			assert(false);
		} catch(NotFoundException nfe) {
			assert(true);
		}
		
	}
	
}
