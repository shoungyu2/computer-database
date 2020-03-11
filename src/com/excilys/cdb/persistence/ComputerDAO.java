package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Companie;
import com.excilys.cdb.model.Computer;


public class ComputerDAO {
	
	private final static String INSERT_COMPUTER="INSERT INTO computer "
			+ "(name,introduced,discontinued, company_id) VALUES (?,?,?,?,?)";
	private final static String SELECT_ALL_COMPUTER=
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id" ;
	private final static String SELECT_COMPUTER=
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.id=?";
	private final static String UPDATE_COMPUTER=
			"UPDATE computer SET introduced=?, discontinued=?, company_id=? WHERE id=?";
	private final static String DELETE_COMPUTER="DELETE FROM computer WHERE id=?";
	
	private static String getComputerNameFromBDD(ResultSet res) throws SQLException{
		
		return res.getString("computer.name");
		
	}

	private static int getComputerIDFromBDD(ResultSet res)throws SQLException{
		
		return res.getInt("computer.id");
		
	}
	
	private static LocalDateTime getComputerIntroDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp introDate=res.getTimestamp("introduced");
		LocalDateTime introLDT=null;
		if(introDate!=null) {
			introLDT=introDate.toLocalDateTime();
		}
		return introLDT;
		
	}

	private static LocalDateTime getComputerDiscDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp discDate=res.getTimestamp("discontinued");
		LocalDateTime discLDT=null;
		if(discDate!=null) {
			discLDT=discDate.toLocalDateTime();
		}
		return discLDT;
		
	}
	
	private static Companie getComputerCompanieFromBDD(ResultSet res) throws SQLException{
		
		int compID=res.getInt("company_id");
		Companie company=
				compID==0?
				null:new Companie(res.getString("company.name"),compID);
		return company;
	}
	
	private static Computer createComputerFromBDD(ResultSet res) throws SQLException {
		
		Computer c;
		
		String name=getComputerNameFromBDD(res);
		int id=getComputerIDFromBDD(res);
		LocalDateTime introLDT=getComputerIntroDateFromBDD(res);
		LocalDateTime discLDT=getComputerDiscDateFromBDD(res);
		Companie company=getComputerCompanieFromBDD(res);
		
		c=new Computer.ComputerBuilder(name,id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setEntreprise(company)
				.build();
		
		return c;
		
	}
	
	private static Timestamp getDateFromComputer(LocalDateTime ldt) {
		
		Timestamp date=
				ldt==null ? 
				null : Timestamp.valueOf(ldt);
		return date;
		
	}
	
	private static int getCompanieIDFromComputer(Computer c) {
		
		return c.getEntreprise()==null?0:c.getEntreprise().getId();
		
	}
	
	public static List<Computer> listComputer(){
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		List<Computer> listComp=new ArrayList<>();
		try {
			ResultSet res=dbc.query(SELECT_ALL_COMPUTER);
			while(res.next()) {
				Computer c=createComputerFromBDD(res);
				listComp.add(c);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComp;
	}
	
	public static Optional<Computer> showDetailComputer(int id) {
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		try (PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPUTER)){
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				Computer c=createComputerFromBDD(res);
				return Optional.of(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Optional.empty();
	}
	
	public static void createComputer(Computer c) {
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		try (PreparedStatement pstmt=dbc.getPreparedStatement(INSERT_COMPUTER)){
			pstmt.setInt(1, c.getID());
			pstmt.setString(2, c.getName());
			Timestamp introDate=getDateFromComputer(c.getIntroductDate());
			pstmt.setTimestamp(3, introDate);
			Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
			pstmt.setTimestamp(4, discDate);			
			int companyID=getCompanieIDFromComputer(c);
			pstmt.setInt(5, companyID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateComputer(Computer c) {
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		try (PreparedStatement pstmt=dbc.getPreparedStatement(UPDATE_COMPUTER)){			
			pstmt.setInt(4, c.getID());
			Timestamp introDate=getDateFromComputer(c.getIntroductDate());
			pstmt.setTimestamp(1, introDate);
			Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
			pstmt.setTimestamp(2, discDate);
			int companyID=getCompanieIDFromComputer(c);
			pstmt.setInt(3, companyID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteComputer(int id) {
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(DELETE_COMPUTER)){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
