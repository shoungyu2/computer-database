package com.excilys.cdb.mapper_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class MapperTest {
	
	@Spy
	VerificationService verifService=new VerificationService();
	
	@Mock
	CompanieDAO cdao=new CompanieDAO();
	
	@Mock
	ComputerDAO computerDAO=new ComputerDAO();
	
	@Test
	public void stringToIntTest() {
		
		Mapper map=new Mapper();
		map.setParseProb(new ArrayList<Problems>());
		
		assertEquals(13, map.stringToID("13"));
		assert(map.stringToID("1.2")==0 && map.getParseProb().size()==1);
		assert(map.stringToID("fhqsdjkf")==0 && map.getParseProb().size()==2);
		assert(map.stringToID("")==0 && map.getParseProb().size()==3);
		assert(map.stringToID(null)==0 && map.getParseProb().size()==4);
		
	}
	
	@Test
	public void stringToDateTest() {
		
		Mapper map=new Mapper();
		map.setParseProb(new ArrayList<Problems>());
		
		LocalDateTime ldt1= LocalDateTime.parse("2019-04-28T00:00:00");
		
		assert(map.stringToDate("28/04/2019").isEqual(ldt1));
		try {
			map.stringToDate("45/02/1900");
			assert(false);
		} catch(DateTimeParseException dtpe) {
			assert(true);
		}
		try {
			map.stringToDate("fhqsduifyzeui");
			assert(false);
		} catch(DateTimeParseException dtpe) {
			assert(true);
		}
		
	}
	
	@Test
	public void stringToCompanieTest() throws NotFoundException{
		
		Mapper map=new Mapper();
		verifService.setCompanieDAO(cdao);
		map.setVerifService(verifService);
		map.setParseProb(new ArrayList<Problems>());
		
		Companie comp=new Companie("343",343);
		Optional<Companie> ocomp=Optional.of(comp);
		
		Mockito.when(cdao.showDetailCompanie(343)).thenReturn(ocomp);
		Mockito.when(verifService.verifIDCompanieInBDD(343)).thenReturn(ocomp);
		Mockito.when(cdao.showDetailCompanie(0)).thenReturn(Optional.empty());
		
		assertEquals((ocomp.get()),map.stringToCompanie("343").get());
		assertEquals(Optional.empty(),map.stringToCompanie("0"));
		assertEquals(1,map.getParseProb().size());
		assertEquals(Optional.empty(),map.stringToCompanie("fsdhjkfhs"));
		assertEquals(3,map.getParseProb().size());
		assertEquals(Optional.empty(),map.stringToCompanie(""));
		assertEquals(Optional.empty(),map.stringToCompanie(null));
		
	}
	
	@Test
	public void stringToComputerTest() throws NotFoundException {
		
		Mapper map=new Mapper();
		verifService.setComputerDAO(computerDAO);
		verifService.setCompanieDAO(cdao);
		map.setVerifService(verifService);
		map.setParseProb(new ArrayList<Problems>());

		Companie comp=new Companie("343",343);
		Optional<Companie> ocomp=Optional.of(comp);
		
		List<String> infoComp1= new ArrayList<String>();
		infoComp1.add("343");
		infoComp1.add("XBox One");
		infoComp1.add(null);
		infoComp1.add(null);
		infoComp1.add(null);
		
		List<String> infoComp2=new ArrayList<String>();
		infoComp2.add("343");
		infoComp2.add("XBox 360");
		infoComp2.add("02/02/2002");
		infoComp2.add("03/03/2003");
		infoComp2.add("343");
		
		List<String> infoComp3=new ArrayList<String>();
		infoComp3.add("0");
		infoComp3.add(null);
		infoComp3.add("fjklfjz");
		infoComp3.add("jfsdfioez");
		infoComp3.add("0");
		
		Computer comp1= new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setEntreprise(null).build();
		
		Computer comp2= new Computer.ComputerBuilder("XBox 360", 343)
				.setIntroductDate(LocalDateTime.parse("2002-02-02T00:00:00"))
				.setDiscontinueDate(LocalDateTime.parse("2003-03-03T00:00:00"))
				.setEntreprise(comp).build();
		
		Computer comp3= new Computer.ComputerBuilder(null, 0)
				.setIntroductDate(null)
				.setDiscontinueDate(null)
				.setEntreprise(null).build();
		
		Mockito.when(computerDAO.showDetailComputer(343)).thenReturn(Optional.of(comp1));
		Mockito.when(verifService.verifIDComputerInBDD(343)).thenReturn(Optional.of(comp1));
		Mockito.when(cdao.showDetailCompanie(343)).thenReturn(ocomp);
		Mockito.when(verifService.verifIDCompanieInBDD(343)).thenReturn(ocomp);
		
		assertEquals(comp1,map.stringToComputer(infoComp1));
		assertEquals(map.getParseProb().size(),0);
		assertEquals(comp2, map.stringToComputer(infoComp2));
		assertEquals(map.getParseProb().size(),0);
		assertNotEquals(comp1, map.stringToComputer(infoComp2));
		assertEquals(map.getParseProb().size(), 0);
		assertEquals(comp3,map.stringToComputer(infoComp3));
		assertEquals(map.getParseProb().size(), 5);
		
	}
	
}
