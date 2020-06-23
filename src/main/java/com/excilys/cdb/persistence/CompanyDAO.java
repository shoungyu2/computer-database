package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {
	
	private final static Logger LOGGER = Logger.getLogger(CompanyDAO.class);
	
	private enum AllCompanyQuery{
		SELECT_ALL_COMPANY("SELECT id,name FROM company ORDER BY id"),
		SELECT_COMPANY("SELECT id,name FROM company WHERE id = ?"),
		DELETE_COMPANY("DELETE FROM company WHERE id = ?"),
		DELETE_ALL_COMPUTER_WITH_COMPANY("DELETE FROM computer WHERE company_id = ?");
	
		private final String query;
		
		private AllCompanyQuery(String query) {
			this.query=query;
		}
		
		public String getQuery() {
			return this.query;
		}
	
	}
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
		String selectAllCompany = AllCompanyQuery.SELECT_ALL_COMPANY.getQuery();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(selectAllCompany)
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
			LOGGER.debug("Requête effectuée: " + loggingQuery(selectAllCompany));
		
		} catch (SQLException e) {
			LOGGER.error("Tentative de requête: " + loggingQuery(selectAllCompany) + " échouée", e);
		}
		
		return listComp;
	
	}
	
	public Optional<Company> showDetailCompany(int id) {
		
		String selectCompany = AllCompanyQuery.SELECT_COMPANY.getQuery();
		
		try(
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(selectCompany)
			){
			
			pstmt.setInt(1, id);
			ResultSet res = pstmt.executeQuery();
			Company c;
			if(res.next()) {
				int idComp = res.getInt("id");
				String name = res.getString("name");
				c = new Company.Builder(idComp)
						.setName(name).build();
				return Optional.of(c);
			}
			LOGGER.debug("Requête effectuée: " + loggingQuery(selectCompany, String.valueOf(id)));
						
		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: " + loggingQuery(selectCompany, String.valueOf(id)) + " échouée", sqle);
		}
		
		return Optional.empty();
	}
	
	public void deleteCompany(int id) {
		
		String deleteCompany = AllCompanyQuery.DELETE_COMPANY.getQuery();
		String deleteAllComputerWithCompany = AllCompanyQuery.DELETE_ALL_COMPUTER_WITH_COMPANY.getQuery();
		Connection dbc = null;
		
		try{

			dbc = DataSourceConnection.getConnection();
			PreparedStatement pstmt_del_company = dbc.prepareStatement(deleteCompany);
			PreparedStatement pstmt_del_computer = dbc.prepareStatement(deleteAllComputerWithCompany);
					
			dbc.setAutoCommit(false);
			
			pstmt_del_computer.setInt(1, id);
			pstmt_del_company.setInt(1, id);
			
			pstmt_del_computer.executeUpdate();
			pstmt_del_company.executeUpdate();
			
			dbc.commit();
			dbc.setAutoCommit(true);
			
			LOGGER.debug("Requêtes effectuées: \n" + loggingQuery(deleteCompany, String.valueOf(id))
			 + "\n" + loggingQuery(deleteAllComputerWithCompany, String.valueOf(id)));
			
		} catch(SQLException sqle) {
			try {
				dbc.rollback();
				LOGGER.error("Tentatives de requête:\n" + loggingQuery(deleteCompany, String.valueOf(id))
				 + "\n" + loggingQuery(deleteAllComputerWithCompany, String.valueOf(id)), sqle);
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
