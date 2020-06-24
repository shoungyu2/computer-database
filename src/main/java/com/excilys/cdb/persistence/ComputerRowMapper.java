package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet res, int rowNum) throws SQLException{
		
		Company company=new Company.Builder(res.getInt("computer.company_id"))
				.setName(res.getString("company.id")).build();
		
		return new Computer.Builder(res.getString("computer.name"), res.getInt("computer.id"))
				.setIntroductDate(res.getTimestamp("computer.introduced").toLocalDateTime())
				.setDiscontinueDate(res.getTimestamp("computer.discontinued").toLocalDateTime())
				.setCompany(company).build();
		
	}
	
}
