package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet res, int rowNum) throws SQLException{
		
		Company company=new Company.Builder(res.getInt("computer.company_id"))
				.setName(res.getString("company.name")).build();
		
		LocalDateTime introLdt=DAOMapper.getComputerIntroDateFromBDD(res);
		LocalDateTime discLdt=DAOMapper.getComputerDiscDateFromBDD(res);
		
		return new Computer.Builder(res.getString("computer.name"), res.getInt("computer.id"))
				.setIntroductDate(introLdt)
				.setDiscontinueDate(discLdt)
				.setCompany(company).build();
		
	}
	
}
