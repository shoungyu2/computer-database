package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDAO {
	
	private enum AllComputerQuery {

		INSERT_COMPUTER("INSERT INTO computer "
				+ "(name,introduced,discontinued, company_id) VALUES (?,?,?,?)") ,
		SELECT_ALL_COMPUTER_WITH_PARAMETER(
				"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
				+ " FROM computer LEFT JOIN company ON company_id=company.id"
				+ " searchParameter filterParameter LIMIT ? OFFSET ?" ) ,
		SELECT_COMPUTER(
				"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
				+ " FROM computer LEFT JOIN company ON company_id(company.id"
				+ " WHERE computer.id=?") ,
		UPDATE_COMPUTER(
				"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?") ,
		DELETE_COMPUTER("DELETE FROM computer WHERE id=?") ,
		GET_NBR_COMPUTER(
				"SELECT COUNT(*) FROM computer "
				+ " WHERE computer.name LIKE ? ");
		
		private String query;
		
		private AllComputerQuery(String query) {
			this.query=query;
		}
		
		public String getQuery() {
			return this.query;
		}
		
	}
	
	private final static Logger LOGGER = Logger.getLogger(ComputerDAO.class);
	
	private void setPreparedStatementForComputer(PreparedStatement preparedStatement, Computer c) throws SQLException {
		
		Timestamp introDate = RawMapper.getDateFromComputer(c.getIntroductDate());
		Timestamp discDate = RawMapper.getDateFromComputer(c.getDiscontinueDate());
		int companyID = RawMapper.getCompanieIDFromComputer(c);
		
		preparedStatement.setString(1, c.getName());
		preparedStatement.setTimestamp(2, introDate);
		preparedStatement.setTimestamp(3, discDate);
		if(companyID == 0) {
			preparedStatement.setNull(4, Types.BIGINT);
		}
		else {
			preparedStatement.setInt(4, companyID);
		}
		
	}
	
	private String setOrderInQuery(String query, String filter, String order) {
		
		if(filter != null) {
			if(order != null && order.equals("asc")) {
				
				switch(filter) {
				
				case "name":
					query=query.replace("filterParameter", "ORDER BY computer.name");
					break;
					
				case "introduced":
					query=query.replace("filterParameter", "ORDER BY computer.introduced");
					break;
					
				case "discontinued":
					query=query.replace("filterParameter", "ORDER BY computer.discontinued");
					break;
					
				case "company_id":
					query=query.replace("filterParameter", "ORDER BY company.name");
					break;
					
				default:
					query=query.replace("filterParameter", "ORDER BY computer.id");
				}
			}
			else {
				
				switch(filter) {
			
				case "name":
					query=query.replace("filterParameter", "ORDER BY computer.name DESC");
					break;
					
				case "introduced":
					query=query.replace("filterParameter", "ORDER BY computer.introduced DESC");
					break;
					
				case "discontinued":
					query=query.replace("filterParameter", "ORDER BY computer.discontinued DESC");
					break;
					
				case "company_id":
					query=query.replace("filterParameter", "ORDER BY company.name DESC");
					break;
					
				default:
					query=query.replace("filterParameter", "ORDER BY computer.id");
				}
					
			}
		}
		else {
			query=query.replace("filterParameter", "ORDER BY computer.id");
		}
					
		return query;
		
	}
	
	private String generateQuery(String search, String filter, String order) {
		
		String query=AllComputerQuery.SELECT_ALL_COMPUTER_WITH_PARAMETER.getQuery();	
			
		if(search != null && !search.isEmpty()) {
			search = search.replace("%", "\\%");
			query=query.replace("searchParameter", "WHERE computer.name LIKE '%"+search+"%'");
		}
		else {
			query=query.replace("searchParameter", "");
		}
		
		query=setOrderInQuery(query, filter, order);
		
		return query;
		
	}
	
	public Optional<Computer> getComputerFromId(int id) {
		
		String selectComputer = AllComputerQuery.SELECT_COMPUTER.getQuery();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(selectComputer)
			){
			pstmt.setInt(1, id);
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				Computer c = RawMapper.createComputerFromBDD(res);
				return Optional.of(c);
			}
			
			LOGGER.debug("Requête effectuée: " + selectComputer);
		} catch (SQLException e) {
			LOGGER.error("Requête refusée: " + selectComputer,e);
		}	
		
		return Optional.empty();
		
	}
	
	public boolean createComputer(Computer c) {
		
		String insertComputer = AllComputerQuery.INSERT_COMPUTER.getQuery();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(insertComputer)
			){
			
			setPreparedStatementForComputer(pstmt, c);
			pstmt.executeUpdate();
			
			LOGGER.debug("Requête effectuée: "+insertComputer);
			return true;
			
		} catch (SQLException e) {
			
			LOGGER.error("Requête refusée: "+insertComputer,e);
			return false;
		
		}
		
	}
	
	public boolean updateComputer(Computer c) {
		
		String updateComputer = AllComputerQuery.UPDATE_COMPUTER.getQuery();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(updateComputer)
			){			
			
			pstmt.setInt(5, c.getId());
			setPreparedStatementForComputer(pstmt, c);
			pstmt.executeUpdate();
		
			LOGGER.debug("Requête effectuée: "+updateComputer);
			return true;
			
		} catch (SQLException e) {
			
			LOGGER.error("Requête refusée: "+updateComputer,e);
			return false;
			
		}
		
	}
	
	public boolean deleteComputer(int id) {
		
		String deleteComputer =  AllComputerQuery.DELETE_COMPUTER.getQuery();
		
		try (
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(deleteComputer)
			){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			LOGGER.debug("Requête effectuée: "+deleteComputer);
			return true;
			
		} catch (SQLException e) {
			
			LOGGER.error("Requête refusée: "+deleteComputer,e);
			return false;
		
		}
		
	}
	
	public int getNbrComputer(String search) {
		
		String getNbrComputer = AllComputerQuery.GET_NBR_COMPUTER.getQuery();
		
		try(
				Connection dbc =  DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(getNbrComputer);
			){
			if(search != null) {
				search = search.replace("%", "\\%");
				pstmt.setString(1, "%" + search + "%");
			}
			else {
				pstmt.setString(1, "%");
			}
			ResultSet res =  pstmt.executeQuery();
			
			LOGGER.debug("Requête effectuée: "+getNbrComputer);
			if(res.next()) {
				return res.getInt(1);
			}
			
		} catch(SQLException sqle) {	
			LOGGER.error("Requête refusée: "+getNbrComputer, sqle);
		}
		
		return 0;
		
	}
	
	public List<Computer> getComputers(String search, String filter, String order, Page page){
		
		List<Computer> listComputers = new ArrayList<Computer>();
		String getComputers = generateQuery(search, filter, order);
		
		try(
				Connection dbc = DataSourceConnection.getConnection();
				PreparedStatement pstmt = dbc.prepareStatement(getComputers);
			){
			
			pstmt.setInt(1, Page.getNbrElements());
			pstmt.setInt(2, page.getOffset());
			
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()) {
				Computer c = RawMapper.createComputerFromBDD(res);
				listComputers.add(c);
			}
			
			LOGGER.debug("Requête effectuée: "+getComputers);
			
		} catch(SQLException sqle) {
			LOGGER.error("Requête refusée: "+getComputers,sqle);;
		}
		
		return listComputers;
		
	}
		
}
