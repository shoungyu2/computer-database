package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet res, int rowNum) throws SQLException{
		
		return new Company.Builder(res.getInt("id"))
				.setName(res.getString("name")).build();
		
	}
	
}
