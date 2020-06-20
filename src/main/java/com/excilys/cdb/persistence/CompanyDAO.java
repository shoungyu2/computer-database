package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.cdb.model.Company;

public class CompanyDAO {
	
	private final static Logger LOGGER = Logger.getLogger(CompanyDAO.class);
	
	public final static	String SELECT_ALL_COMPANY = "SELECT id,name FROM company"
			 +  " ORDER BY id";
	public final static String SELECT_COMPANY = "SELECT id,name FROM company WHERE id = ?";
	public final static String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	public final static String DELETE_ALL_COMPUTER_WITH_COMPANY = "DELETE FROM computer WHERE company_id = ?";
	
	private String loggingQuery(String query, String...params) {
		
		String[] str = query.split("\\?");
		String finalQuery = "";
		
		for(int i = 0;i<str.length-1;i++) {
			finalQuery += str[i] + params[i];
		}
		
		return finalQuery + str[str.length-1];
		
	}
	
	public List<Company> listCompany(){
		
		List<Company> listComp = new ArrayList<>();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(SELECT_ALL_COMPANY)
			){
			
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()) {
				Company c;
				String name = res.getString("name");
				int id = res.getInt("id");
				c = new Company.Builder(id)
					.setName(name).build();
				listComp.add(c);
			}
			LOGGER.info("Requête effectuée: " + loggingQuery(SELECT_ALL_COMPANY));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Tentative de requête: " + loggingQuery(SELECT_ALL_COMPANY) + " échouée", e);
		}
		
		return listComp;
	
	}
	
	public Optional<Company> showDetailCompany(int id) {
		
		try(
				Connection dbc  =  DataSourceConnection.getConnection();
				PreparedStatement pstmt  =  dbc.prepareStatement(SELECT_COMPANY)
			){
			
			pstmt.setInt(1, id);
			ResultSet res  =  pstmt.executeQuery();
			Company c;
			if(res.next()) {
				int idComp  =  res.getInt("id");
				String name  =  res.getString("name");
				c = new Company.Builder(idComp)
						.setName(name).build();
				return Optional.of(c);
			}
			LOGGER.info("Requête effectuée: " + loggingQuery(SELECT_COMPANY, String.valueOf(id)));
						
		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: " + loggingQuery(SELECT_COMPANY, String.valueOf(id)) + " échouée", sqle);
		}
		
		return Optional.empty();
	}
	
	public void deleteCompany(int id) {
		
		Connection dbc = null;
		try{

			dbc  =  DataSourceConnection.getConnection();
			PreparedStatement pstmt_del_company = dbc.prepareStatement(DELETE_COMPANY);
			PreparedStatement pstmt_del_computer = dbc.prepareStatement(DELETE_ALL_COMPUTER_WITH_COMPANY);
					
			dbc.setAutoCommit(false);
			
			pstmt_del_computer.setInt(1, id);
			pstmt_del_company.setInt(1, id);
			
			pstmt_del_computer.executeUpdate();
			pstmt_del_company.executeUpdate();
			
			dbc.commit();
			dbc.setAutoCommit(true);
			
			LOGGER.info("Requêtes effectuées: \n" + loggingQuery(DELETE_COMPANY, String.valueOf(id))
			 + "\n" + loggingQuery(DELETE_ALL_COMPUTER_WITH_COMPANY, String.valueOf(id)));
			
		} catch(SQLException sqle) {
			try {
				dbc.rollback();
				LOGGER.error("Tentatives de requête:\n" + loggingQuery(DELETE_COMPANY, String.valueOf(id))
				 + "\n" + loggingQuery(DELETE_ALL_COMPUTER_WITH_COMPANY, String.valueOf(id)), sqle);
			} catch (SQLException sqle2) {
				sqle2.printStackTrace();
			}
			sqle.printStackTrace();
		}
		finally {
			try {
				dbc.close();
			} catch(SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
	}
	
}
