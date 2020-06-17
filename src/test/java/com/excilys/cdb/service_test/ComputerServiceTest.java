package com.excilys.cdb.service_test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.InvalidEntryException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceTest {

	@Mock
	ComputerDAO computerDAO;
	
	@Mock
	VerificationService verifService;
	
	@Mock
	Mapper map;
	
	ComputerService compService=new ComputerService();
	
	@Test
	public void getNbrComputerServiceTest() {
		
		compService.setCompDAO(computerDAO);
		
		Mockito.when(computerDAO.getNbrComputer("")).thenReturn(574);
		
		assertEquals(574, compService.getNbrComputerService(""));
		
	}
	
	@Test
	public void listComputerTest() {
		
		compService.setCompDAO(computerDAO);
		
		Page page= new Page(343);
		
		Mockito.when(computerDAO.listComputer(page)).thenReturn(new ArrayList<Computer>());
		
		assertEquals(new ArrayList<Computer>(),compService.listComputerService(page));
		
	}
	
	@Test
	public void showDetailComputerServiceTest() throws InvalidEntryException, NotFoundException {
		
		compService.setMap(map);
		compService.setVerifServ(verifService);
		compService.setCompDAO(computerDAO);
		
		Computer comp=new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setCompany(null)
				.build();
		Optional<Computer> oComp=Optional.of(comp);
		
		Mockito.when(map.stringToID("-1")).thenReturn(-1);
		Mockito.when(map.stringToID("blablou")).thenThrow(new NumberFormatException());
		Mockito.when(map.stringToID("0")).thenReturn(0);
		Mockito.when(map.stringToID("343")).thenReturn(343);
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDComputerInBDD(0);
		Mockito.when(computerDAO.showDetailComputer(343)).thenReturn(oComp);
		
		assertEquals(Optional.empty(), compService.showDetailComputerService("-1"));
		try {
			compService.showDetailComputerService("blablou");
			assert(false);
		} catch(InvalidEntryException iee) {
			assert(true);
		}
		try {
			compService.showDetailComputerService("0");
			assert(false);
		} catch(InvalidEntryException iee) {
			assert(true);
		}
		assertEquals(oComp, compService.showDetailComputerService("343"));
		
	}

	@Test
	public void createComputerServiceTest() throws ComputerIsNullException {
		
		compService.setMap(map);
		compService.setVerifServ(verifService);
		compService.setCompDAO(computerDAO);
		
		ComputerDTO cdtoEmpty=new ComputerDTO.ComputerDTOBuilder("0", null)
				.setIntroduced(null)
				.setDiscontinued(null)
				.setCompanyDTO(null)
				.build();
		
		ComputerDTO cdto=new ComputerDTO.ComputerDTOBuilder("343", "XBox One")
				.setIntroduced("2002-02-02")
				.setDiscontinued("2003-03-03")
				.setCompanyDTO(null)
				.build();
		
		Computer compEmpty=new Computer.ComputerBuilder(null, 0)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setCompany(null)
				.build();
		
		Computer comp= new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(LocalDateTime.parse("2002-02-02T00:00:00"))
				.setDiscontinueDate(LocalDateTime.parse("2003-03-03T00:00:00"))
				.setCompany(null)
				.build();
		
		ArrayList<Problems> listProbs1=new ArrayList<Problems>();
		listProbs1.add(Problems.createNameIsNullProblem(null));
		
		Mockito.when(map.getParseProb()).thenReturn(new ArrayList<Problems>());
		Mockito.when(map.stringToComputer(cdtoEmpty)).thenReturn(compEmpty);
		Mockito.when(map.stringToComputer(cdto)).thenReturn(comp);
		Mockito.doCallRealMethod().when(map).setParseProb(new ArrayList<Problems>());
		Mockito.doCallRealMethod().when(verifService).verifNameIsNotNull(null, new ArrayList<Problems>());
		Mockito.doCallRealMethod().when(verifService).verifDate(null, null, listProbs1);
		Mockito.doNothing().when(computerDAO).createComputer(comp);
		
		try {
			compService.createComputerService(cdto);
			assert(true);
			compService.createComputerService(cdtoEmpty);
			assert(false);
		} catch (InvalidEntryException iee) {
			assertEquals(1, iee.getListProb().size());
		}
		
		
	}

	@Test
	public void updateComputerServiceTest() throws ComputerIsNullException, NotFoundException {
		
		compService.setMap(map);
		compService.setVerifServ(verifService);
		compService.setCompDAO(computerDAO);
		
		ComputerDTO cdtoEmpty=new ComputerDTO.ComputerDTOBuilder("0", null)
				.setIntroduced(null)
				.setDiscontinued(null)
				.setCompanyDTO(null)
				.build();
		
		ComputerDTO cdto=new ComputerDTO.ComputerDTOBuilder("343", "XBox One")
				.setIntroduced("02/02/2002")
				.setDiscontinued("03/03/2003")
				.setCompanyDTO(null)
				.build();
		
		Computer compEmpty=new Computer.ComputerBuilder(null, 0)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setCompany(null)
				.build();
		
		Computer comp= new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(LocalDateTime.parse("2002-02-02T00:00:00"))
				.setDiscontinueDate(LocalDateTime.parse("2003-03-03T00:00:00"))
				.setCompany(null)
				.build();
		
		ArrayList<Problems> listProbs1=new ArrayList<Problems>();
		listProbs1.add(Problems.createNameIsNullProblem(null));
		
		Mockito.when(map.getParseProb()).thenReturn(new ArrayList<Problems>());
		Mockito.when(map.stringToComputer(cdtoEmpty)).thenReturn(compEmpty);
		Mockito.when(map.stringToComputer(cdto)).thenReturn(comp);
		Mockito.doCallRealMethod().when(map).setParseProb(new ArrayList<Problems>());
		Mockito.doCallRealMethod().when(verifService).verifNameIsNotNull(null, new ArrayList<Problems>());
		Mockito.doCallRealMethod().when(verifService).verifDate(null, null, listProbs1);
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDComputerInBDD(0);
		Mockito.doNothing().when(computerDAO).updateComputer(comp);
		
		try {
			compService.updateComputerService(cdto);
			assert(true);
			compService.updateComputerService(cdtoEmpty);
		} catch(InvalidEntryException iee) {
			assertEquals(2, iee.getListProb().size());
		}
		
		
	}

	@Test
	public void deleteComputerServiceTest() throws NotFoundException {
		
		compService.setMap(map);
		compService.setVerifServ(verifService);
		compService.setCompDAO(computerDAO);
		
		Mockito.when(map.stringToID("blablou")).thenThrow(new NumberFormatException());
		Mockito.when(map.stringToID("0")).thenReturn(0);
		Mockito.when(map.stringToID("343")).thenReturn(343);
		Mockito.doThrow(new NotFoundException()).when(verifService).verifIDComputerInBDD(0);
		Mockito.doNothing().when(verifService).verifIDComputerInBDD(343);
		Mockito.doNothing().when(computerDAO).deleteComputer(343);
		
		
		try {
			compService.deleteComputerService("343");
			assert(true);
			compService.deleteComputerService("blablou");
			assert(false);
		} catch(InvalidEntryException iee) {
			assertEquals(1, iee.getListProb().size());
		}
		
		try {
			compService.deleteComputerService("0");
			assert(false);
		} catch(InvalidEntryException iee) {
			assertEquals(1, iee.getListProb().size());
		}
		
	}
	
}
