package com.excilys.cdb.mapper_test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.CompanyIsNullException;
import com.excilys.cdb.exception.ComputerIsNullException;
import com.excilys.cdb.exception.Problems;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;

public class MapperTest {

	Mapper map=new Mapper();
	
	@Test
	public void stringToIDTest() {
		
		map.setParseProb(new ArrayList<Problems>());
		
		String nbr="24";
		String nbrFloat="12.5";
		String notNbr="fjsdklfj";
		String empty="";
		String nullStr=null;
		
		assertEquals(24,map.stringToID(nbr));
		assertEquals(0, map.stringToID(nbrFloat));
		assertEquals(0, map.stringToID(notNbr));
		assertEquals(0, map.stringToID(empty));
		assertEquals(0, map.stringToID(nullStr));
		assertEquals(4, map.getParseProb().size());
		
	}
	
	@Test
	public void stringToDateTest() {
		
		map.setParseProb(new ArrayList<Problems>());
		
		String validDate="07/06/2020";
		String invalidDate="35/02/2002";
		String invalidDateformat="06/02/98";
		String invalidString="jklsfjk";
		String empty="";
		String nullStr=null;
		
		LocalDateTime validLDT=LocalDateTime.parse("2020-06-07T00:00:00");
		
		assertEquals(validLDT, map.stringToDate(validDate));
		assertEquals(null, map.stringToDate(invalidDate));
		assertEquals(null, map.stringToDate(invalidDateformat));
		assertEquals(null, map.stringToDate(invalidString));
		assertEquals(null, map.stringToDate(empty));
		assertEquals(null, map.stringToDate(nullStr));
		assertEquals(3, map.getParseProb().size());
		
	}

	@Test
	public void stringToCompanyTest() throws CompanyIsNullException {
		
		map.setParseProb(new ArrayList<Problems>());
		
		try {
			map.stringToCompanie(null);
			assert(false);
		} catch (CompanyIsNullException cine) {
			assert(true);
		}
		
		CompanyDTO cDTOComplete= new CompanyDTO.CompanyDTOBuilder("343")
				.setName("343 Industries").build();
		
		CompanyDTO cDTOwithIDNull= new CompanyDTO.CompanyDTOBuilder(null)
				.setName("343 Industries").build();
		
		CompanyDTO cDTOwithIDEmpty= new CompanyDTO.CompanyDTOBuilder("")
				.setName("343 Industries").build();
		
		CompanyDTO cDTOwithoutName= new CompanyDTO.CompanyDTOBuilder("343")
				.setName(null).build();
		
		CompanyDTO cDTOempty=new CompanyDTO.CompanyDTOBuilder(null)
				.setName(null).build();
		
		Companie compComplete= new Companie("343 Industries", 343);
		Companie compWithoutName= new Companie(null, 343);
		
		assertEquals(Optional.of(compComplete), map.stringToCompanie(cDTOComplete));
		assertEquals(Optional.empty(), map.stringToCompanie(cDTOwithIDNull));
		assertEquals(Optional.empty(), map.stringToCompanie(cDTOwithIDEmpty));
		assertEquals(Optional.of(compWithoutName), map.stringToCompanie(cDTOwithoutName));
		assertEquals(Optional.empty(), map.stringToCompanie(cDTOempty));
		
	}

	@Test
	public void stringToComputer() throws CompanyIsNullException, ComputerIsNullException {
		
		map.setParseProb(new ArrayList<Problems>());
		
		try {
			map.stringToComputer(null);
			assert(false);
		} catch (ComputerIsNullException cine) {
			assert(true);
		}
		
		CompanyDTO companyDTO=new CompanyDTO.CompanyDTOBuilder("343")
				.setName("343 Industries").build();
		
		Companie company=new Companie("343 Industries", 343);
		
		ComputerDTO computerDTOComplete= new ComputerDTO.ComputerDTOBuilder("343", "XBox One")
				.setIntroduced("02/02/2002")
				.setDiscontinued("03/03/2003")
				.setCompanyDTO(companyDTO).build();
		
		
		Computer computerComplete= new Computer.ComputerBuilder("XBox One", 343)
				.setIntroductDate(LocalDateTime.parse("2002-02-02T00:00:00"))
				.setDiscontinueDate(LocalDateTime.parse("2003-03-03T00:00:00"))
				.setEntreprise(company).build();
		
		assertEquals(computerComplete, map.stringToComputer(computerDTOComplete));
		
	}

}
