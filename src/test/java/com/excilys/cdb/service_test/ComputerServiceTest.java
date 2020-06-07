package com.excilys.cdb.service_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
	public void listComputerTest() {
		
		compService.setCompDAO(computerDAO);
		
		Page page= new Page(343);
		
		Mockito.when(computerDAO.listComputer(page)).thenReturn(new ArrayList<Computer>());
		
		assertEquals(new ArrayList<Computer>(),compService.listComputerService(page));
		
	}
	
}
