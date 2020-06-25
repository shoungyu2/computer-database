package com.excilys.cdb.persistence;

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
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.mapper.CompanyRowMapper;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {
	
	private final static Logger LOGGER = Logger.getLogger(CompanyDAO.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private enum AllCompanyQuery{
		SELECT_ALL_COMPANY("SELECT id,name FROM company ORDER BY id"),
		SELECT_COMPANY("SELECT id,name FROM company WHERE id = :id"),
		DELETE_COMPANY("DELETE FROM company WHERE id = :id"),
		DELETE_ALL_COMPUTER_WITH_COMPANY("DELETE FROM computer WHERE company_id = :company_id");
	
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
		
		try {
			
			listComp = namedParameterJdbcTemplate.query(selectAllCompany, new CompanyRowMapper());
			LOGGER.debug("Requête effectuée: " + loggingQuery(selectAllCompany));
		
		} catch (DataAccessException dae) {
			LOGGER.error("Tentative de requête: " + loggingQuery(selectAllCompany) + " échouée", dae);
		}
		
		return listComp;
	
	}
	
	public Optional<Company> showDetailCompany(int id) {
		
		String selectCompany = AllCompanyQuery.SELECT_COMPANY.getQuery();
		
		try{
			
			SqlParameterSource sqlParameterSource=new MapSqlParameterSource()
					.addValue("id", id);
			Company company=namedParameterJdbcTemplate.queryForObject(selectCompany, sqlParameterSource, new CompanyRowMapper());
			LOGGER.debug("Requête effectuée: " + loggingQuery(selectCompany, String.valueOf(id)));
			
			return Optional.ofNullable(company);
						
		} catch(DataAccessException dae) {
			LOGGER.error("Tentative de requête: " + loggingQuery(selectCompany, String.valueOf(id)) + " échouée", dae);
		}
		
		return Optional.empty();
	}
	
	@Transactional
	public void deleteCompany(int id) {
		
		String deleteCompany = AllCompanyQuery.DELETE_COMPANY.getQuery();
		String deleteAllComputerWithCompany = AllCompanyQuery.DELETE_ALL_COMPUTER_WITH_COMPANY.getQuery();
		
		try{

			SqlParameterSource sqlParameterSource=new MapSqlParameterSource()
					.addValue("company_id", id);
			namedParameterJdbcTemplate.update(deleteAllComputerWithCompany, sqlParameterSource);
			
			sqlParameterSource=new MapSqlParameterSource()
					.addValue("id", id);
			namedParameterJdbcTemplate.update(deleteCompany, sqlParameterSource);
			
			LOGGER.debug("Requêtes effectuées: \n" + loggingQuery(deleteCompany, String.valueOf(id))
			 + "\n" + loggingQuery(deleteAllComputerWithCompany, String.valueOf(id)));
			
		} catch(DataAccessException dae) {
				LOGGER.error("Tentatives de requête:\n" + loggingQuery(deleteCompany, String.valueOf(id))
				 + "\n" + loggingQuery(deleteAllComputerWithCompany, String.valueOf(id)), dae);			
		}
		
	}
	
}
