package com.excilys.cdb.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
public class ComputerDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private enum AllComputerQuery {

		INSERT_COMPUTER("INSERT INTO computer "
				+ "(name,introduced,discontinued, company_id)"
				+ " VALUES ( :name , :introduced , :discontinued , :company_id )") ,
		SELECT_ALL_COMPUTER_WITH_PARAMETER(
				"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
				+ " FROM computer LEFT JOIN company ON computer.company_id=company.id"
				+ " searchParameter filterParameter LIMIT :limit OFFSET :offset" ) ,
		SELECT_COMPUTER(
				"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
				+ " FROM computer LEFT JOIN company ON company_id=company.id"
				+ " WHERE computer.id= :id") ,
		UPDATE_COMPUTER(
				"UPDATE computer SET name= :name , introduced= :introduced , discontinued= :discontinued , company_id= :company_id WHERE id= :id") ,
		DELETE_COMPUTER("DELETE FROM computer WHERE id= :id") ,
		GET_NBR_COMPUTER(
				"SELECT COUNT(*) FROM computer "
				+ " WHERE computer.name LIKE :search ");
		
		private String query;
		
		private AllComputerQuery(String query) {
			this.query=query;
		}
		
		public String getQuery() {
			return this.query;
		}
		
	}
	
	private final static Logger LOGGER = Logger.getLogger(ComputerDAO.class);
	
	private SqlParameterSource setParameterSourceForComputer(Computer c){
		
		Timestamp introDate = RawMapper.getDateFromComputer(c.getIntroductDate());
		Timestamp discDate = RawMapper.getDateFromComputer(c.getDiscontinueDate());
		int companyID = RawMapper.getCompanieIDFromComputer(c);	
		
		return new MapSqlParameterSource()
				.addValue("id", c.getId())
				.addValue("name", c.getName())
				.addValue("introduced", introDate)
				.addValue("discontinued", discDate)
				.addValue("company_id", companyID);
		
	}
	
	private String setOrderInQuery(String query, String filter, String order) {
		
		if(filter==null) {
			filter="";
		}
		if(order != null && order.equals("desc")) {
			order="DESC";
		}
		else {
			order="ASC";
		}
		switch(filter) {
			
		case "name":
			query=query.replace("filterParameter", "ORDER BY computer.name "+order);
			break;
			
		case "introduced":
			query=query.replace("filterParameter", "ORDER BY computer.introduced "+order);
			break;
			
		case "discontinued":
			query=query.replace("filterParameter", "ORDER BY computer.discontinued "+order);
			break;
			
		case "company_id":
			query=query.replace("filterParameter", "ORDER BY company.name "+order);
			break;
			
		default:
			query=query.replace("filterParameter", "ORDER BY computer.id "+order);
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
		
		try{
			
			SqlParameterSource sqlParameterSource= new MapSqlParameterSource()
					.addValue("id", id);
			Computer computer=namedParameterJdbcTemplate.queryForObject(selectComputer, sqlParameterSource, new ComputerRowMapper());
			LOGGER.debug("Requête effectuée: " + selectComputer);
		
			return Optional.ofNullable(computer);
			
		} catch (DataAccessException dae) {
			LOGGER.error("Requête refusée: " + selectComputer,dae);
		}	
		
		return Optional.empty();
		
	}
	
	public boolean createComputer(Computer c) {
		
		String insertComputer = AllComputerQuery.INSERT_COMPUTER.getQuery();
		
		try{
			
			SqlParameterSource sqlParameterSource=setParameterSourceForComputer(c);
			namedParameterJdbcTemplate.update(insertComputer, sqlParameterSource);
			LOGGER.debug("Requête effectuée: "+insertComputer);
			
			return true;
			
		} catch (DataAccessException dae) {
			
			LOGGER.error("Requête refusée: "+insertComputer,dae);
			return false;
		
		}
		
	}
	
	public boolean updateComputer(Computer c) {
		
		String updateComputer = AllComputerQuery.UPDATE_COMPUTER.getQuery();
		
		try{			
		
			SqlParameterSource sqlParameterSource=setParameterSourceForComputer(c);
			namedParameterJdbcTemplate.update(updateComputer, sqlParameterSource);
			LOGGER.debug("Requête effectuée: "+updateComputer);
			
			return true;
			
		} catch (DataAccessException dae) {
			
			LOGGER.error("Requête refusée: "+updateComputer,dae);
			return false;
			
		}
		
	}
	
	public boolean deleteComputer(int id) {
		
		String deleteComputer =  AllComputerQuery.DELETE_COMPUTER.getQuery();
		
		try{
			
			SqlParameterSource sqlParameterSource=new MapSqlParameterSource()
					.addValue("id", id);
			namedParameterJdbcTemplate.update(deleteComputer, sqlParameterSource);
			LOGGER.debug("Requête effectuée: "+deleteComputer);
			return true;
			
		} catch (DataAccessException dae) {
			
			LOGGER.error("Requête refusée: "+deleteComputer,dae);
			return false;
		
		}
		
	}
	
	public int getNbrComputer(String search) {
		
		String getNbrComputer = AllComputerQuery.GET_NBR_COMPUTER.getQuery();
		
		try{
			if(search!=null) {
				search=search.replace("%", "\\%");
			}
			else {
				search="";
			}
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("search", "%"+search+"%");
			int res=namedParameterJdbcTemplate.queryForObject(getNbrComputer, namedParameters, Integer.class);
			LOGGER.debug("Requête effectuée: "+getNbrComputer);

			return res;
			
		} catch(DataAccessException dae) {	
			LOGGER.error("Requête refusée: "+getNbrComputer, dae);
		}
		
		return 0;
		
	}
	
	public List<Computer> getComputers(String search, String filter, String order, Page page){
		
		List<Computer> listComputers = new ArrayList<Computer>();
		String getComputers = generateQuery(search, filter, order);
		
		try{
			
			SqlParameterSource namedParameters= new MapSqlParameterSource()
					.addValue("limit", Page.getNbrElements())
					.addValue("offset", page.getOffset());
			listComputers=namedParameterJdbcTemplate.query(getComputers, namedParameters, new ComputerRowMapper());
			LOGGER.debug("Requête effectuée: "+getComputers);
			
		} catch(DataAccessException dae) {
			LOGGER.error("Requête refusée: "+getComputers,dae);
		}
		
		return listComputers;
		
	}
		
}
