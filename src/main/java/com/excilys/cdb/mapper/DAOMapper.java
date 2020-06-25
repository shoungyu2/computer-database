package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DAOMapper {

	public static String getComputerNameFromBDD(ResultSet res) throws SQLException{
		
		return res.getString("computer.name");
		
	}

	public static int getComputerIDFromBDD(ResultSet res)throws SQLException{
		
		return res.getInt("computer.id");
		
	}
	
	public static LocalDateTime getComputerIntroDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp introDate = res.getTimestamp("introduced");
		LocalDateTime introLDT = null;
		if(introDate != null) {
			introLDT = introDate.toLocalDateTime();
		}
		return introLDT;
		
	}

	public static LocalDateTime getComputerDiscDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp discDate = res.getTimestamp("discontinued");
		LocalDateTime discLDT = null;
		if(discDate != null) {
			discLDT = discDate.toLocalDateTime();
		}
		return discLDT;
		
	}
	
	public static Company getComputerCompanyFromBDD(ResultSet res) throws SQLException{
		
		int compID = res.getInt("company_id");
		Company company = 
				compID == 0?
				null:new Company.Builder(compID).setName(res.getString("company.name")).build();
		return company;
	}
	
	public static Computer createComputerFromBDD(ResultSet res) throws SQLException {
		
		Computer c;
		
		String name = getComputerNameFromBDD(res);
		int id = getComputerIDFromBDD(res);
		LocalDateTime introLDT = getComputerIntroDateFromBDD(res);
		LocalDateTime discLDT = getComputerDiscDateFromBDD(res);
		Company company = getComputerCompanyFromBDD(res);
		
		c = new Computer.Builder(name,id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setCompany(company)
				.build();
		
		return c;
		
	}
	
	public static Timestamp getDateFromComputer(LocalDateTime ldt) {
		
		Timestamp date = 	
				ldt == null ? null : Timestamp.valueOf(ldt);
		return date;
		
	}
	
	public static int getCompanieIDFromComputer(Computer c) {
		
		return c.getCompany() == null?0:c.getCompany().getId();
		
	}
	
}
