package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;


public class ComputerDAO {
	
	private final static Logger LOGGER=Logger.getLogger(ComputerDAO.class);
	
	private String loggingQuery(String query, String...params) {
		
		String[] str=query.split("\\?");
		String finalQuery="";
		
		for(int i=0;i<str.length-1;i++) {
			finalQuery+=str[i]+params[i];
		}
		
		return finalQuery+str[str.length-1];
		
	}
	
		
	private String getComputerNameFromBDD(ResultSet res) throws SQLException{
		
		return res.getString("computer.name");
		
	}

	private int getComputerIDFromBDD(ResultSet res)throws SQLException{
		
		return res.getInt("computer.id");
		
	}
	
	private LocalDateTime getComputerIntroDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp introDate=res.getTimestamp("introduced");
		LocalDateTime introLDT=null;
		if(introDate!=null) {
			introLDT=introDate.toLocalDateTime();
		}
		return introLDT;
		
	}

	private LocalDateTime getComputerDiscDateFromBDD(ResultSet res) throws SQLException{
		
		Timestamp discDate=res.getTimestamp("discontinued");
		LocalDateTime discLDT=null;
		if(discDate!=null) {
			discLDT=discDate.toLocalDateTime();
		}
		return discLDT;
		
	}
	
	private Company getComputerCompanyFromBDD(ResultSet res) throws SQLException{
		
		int compID=res.getInt("company_id");
		Company company=
				compID==0?
				null:new Company(res.getString("company.name"),compID);
		return company;
	}
	
	private Computer createComputerFromBDD(ResultSet res) throws SQLException {
		
		Computer c;
		
		String name=getComputerNameFromBDD(res);
		int id=getComputerIDFromBDD(res);
		LocalDateTime introLDT=getComputerIntroDateFromBDD(res);
		LocalDateTime discLDT=getComputerDiscDateFromBDD(res);
		Company company=getComputerCompanyFromBDD(res);
		
		c=new Computer.ComputerBuilder(name,id)
				.setIntroductDate(introLDT)
				.setDiscontinueDate(discLDT)
				.setCompany(company)
				.build();
		
		return c;
		
	}
	
	private Timestamp getDateFromComputer(LocalDateTime ldt) {
		
		Timestamp date=	
				ldt==null ? null : Timestamp.valueOf(ldt);
		return date;
		
	}
	
	private int getCompanieIDFromComputer(Computer c) {
		
		return c.getCompany()==null?0:c.getCompany().getId();
		
	}
	
	public List<Computer> listComputer(Page page){
		
		String selectAllComputer=AllComputerQuery.SELECT_ALL_COMPUTER.getQuery();
		
		List<Computer> listComp=new ArrayList<>();
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(selectAllComputer)
			){
			pstmt.setInt(1, Page.getNbrElements());
			pstmt.setInt(2, page.getOffset());
			ResultSet res=pstmt.executeQuery();
			while(res.next()) {
				Computer c=createComputerFromBDD(res);
				listComp.add(c);
			}
			
			LOGGER.info("Requête effectuée: "+loggingQuery(selectAllComputer,
					String.valueOf(Page.getNbrElements()), String.valueOf(page.getOffset())));
			
		} catch (SQLException e) {
			LOGGER.error("Tentative de requête: "+loggingQuery(selectAllComputer,
					String.valueOf(Page.getNbrElements()), String.valueOf(page.getOffset()))+" échouée", e);
		}
		
		return listComp;
		
	}
	
	public Optional<Computer> showDetailComputer(int id) {
		
		String selectComputer=AllComputerQuery.SELECT_COMPUTER.getQuery();
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(selectComputer)
			){
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				Computer c=createComputerFromBDD(res);
				return Optional.of(c);
			}

			LOGGER.info("Requête effectuée: "+loggingQuery(selectComputer, String.valueOf(id)));
			
		} catch (SQLException e) {

			LOGGER.error("Tentative de requête : "+loggingQuery(selectComputer)+" échouée");
		}		
		return Optional.empty();
	}
	
	public void createComputer(Computer c) {
		
		String insertComputer=AllComputerQuery.INSERT_COMPUTER.getQuery();
		
		Timestamp introDate=getDateFromComputer(c.getIntroductDate());
		Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
		int companyID=getCompanieIDFromComputer(c);
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(insertComputer)
			){
			pstmt.setString(1, c.getName());
			pstmt.setTimestamp(2, introDate);
			pstmt.setTimestamp(3, discDate);			
			if(companyID==0) {
				pstmt.setNull(4, Types.BIGINT);
			}
			else {
				pstmt.setInt(4, companyID);
			}
			pstmt.executeUpdate();
			
			LOGGER.info("Requête effectuée: "+
			loggingQuery(insertComputer, c.getName(), 
					introDate.toString(),discDate.toString(),String.valueOf(companyID)));
			
		} catch (SQLException e) {
			LOGGER.info("Tentative de requête: "+
			loggingQuery(insertComputer, c.getName(), 
					introDate.toString(),discDate.toString(),String.valueOf(companyID))+" échouée", e);
					
		}
		
		int max=getNbrComputer("")/Page.getNbrElements()+1;
		if(max>Page.getNbrPages()) {
			Page.setNbrPages(max);
		}
		
	}
	
	public void updateComputer(Computer c) {
		
		String updateComputer=AllComputerQuery.UPDATE_COMPUTER.getQuery();
		
		Timestamp introDate=getDateFromComputer(c.getIntroductDate());
		Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
		int companyID=getCompanieIDFromComputer(c);

		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(updateComputer)
			){			
			pstmt.setInt(5, c.getId());
			pstmt.setString(1, c.getName());
			pstmt.setTimestamp(2, introDate);
			pstmt.setTimestamp(3, discDate);
			if(companyID==0) {
				pstmt.setNull(4, Types.BIGINT);
			}
			else {
				pstmt.setInt(4, companyID);
			}
			pstmt.executeUpdate();
			
			LOGGER.info("Requête effectuée: "+
					loggingQuery(updateComputer, String.valueOf(c.getId()), c.getName(), 
					introDate.toString(),discDate.toString(),String.valueOf(companyID)));
					
		} catch (SQLException e) {
			LOGGER.error("Tentative de requête: "+
					loggingQuery(updateComputer, String.valueOf(c.getId()), c.getName(), 
					introDate.toString(),discDate.toString(),String.valueOf(companyID))+" échouée",e);
		}
		
	}
	
	public void deleteComputer(int id) {
		
		String deleteComputer= AllComputerQuery.DELETE_COMPUTER.getQuery();
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(deleteComputer)
			){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			LOGGER.info("Requête effectuée: "+loggingQuery(deleteComputer, String.valueOf(id)));
		
		} catch (SQLException e) {
			LOGGER.error("Tentative de requête: "+loggingQuery(deleteComputer, String.valueOf(id))+" échouée",e);
		}
		int min=getNbrComputer("")/Page.getNbrElements()+1;
		if(min<Page.getNbrPages()) {
			Page.setNbrPages(min);
		}
		
	}
	
	public int getNbrComputer(String search) {
		
		String getNbrComputer=AllComputerQuery.GET_NBR_COMPUTER.getQuery();
		
		try(
				Connection dbc= DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(getNbrComputer);
			){
			if(search!=null) {
				search=search.replace("%", "\\%");
				pstmt.setString(1, "%"+search+"%");
			}
			else {
				pstmt.setString(1, "%");
			}
			ResultSet res= pstmt.executeQuery();
			if(res.next()) {
				return res.getInt(1);
			}
			
			LOGGER.info("Requête effectuée: "+loggingQuery(getNbrComputer, "%"+search+"%"));

		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: "+loggingQuery(getNbrComputer, "%"+search+"%")+" échouée",sqle);
		}
		return 0;
		
	}
	
	public List<Computer> searchComputer(String search, Page page){
		
		List<Computer> searchResult=new ArrayList<Computer>();
		String searchComputer=AllComputerQuery.SEARCH_COMPUTER.getQuery();
		
		try(
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(searchComputer);
			){
			if(search!=null) {
				search=search.replace("%", "\\%");
				pstmt.setString(1, "%"+search+"%");
			}
			else {
				pstmt.setString(1, "%");
			}
			pstmt.setInt(2, Page.getNbrElements());
			pstmt.setInt(3, page.getOffset());
			ResultSet res=pstmt.executeQuery();
			while(res.next()) {
				Computer c=createComputerFromBDD(res);
				searchResult.add(c);
			}
			
			LOGGER.info("Requête effectuée: "+loggingQuery(searchComputer, "%"+search+"%",
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset())));
			
		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: "+loggingQuery(searchComputer, "%"+search+"%",
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset()))+" échouée",sqle);
		}
		
		return searchResult;
		
	}
	
	private List<Computer> orderByWithoutSearch(Page page, String query){
		
		List<Computer> resOrder=new ArrayList<Computer>();
		
		try(
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(query);
			){
			
			pstmt.setInt(1, Page.getNbrElements());
			pstmt.setInt(2, page.getOffset());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				resOrder.add(createComputerFromBDD(rs));
			}
			
			LOGGER.info("Requête effectuée: "+loggingQuery(query, 
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset())));
			
		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: "+loggingQuery(query,
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset()))+" échouée",sqle);		
		}
		
		return resOrder;
		
	}
	
	private List<Computer> orderByWithSearch(Page page, String query, String search){
		
		List<Computer> resOrder=new ArrayList<Computer>();
		
		try(
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(query);
			){
			search=search.replace("%","\\%");
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, Page.getNbrElements());
			pstmt.setInt(3, page.getOffset());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				resOrder.add(createComputerFromBDD(rs));
			}
			
			LOGGER.info("Requête effectuée: "+loggingQuery(query, "%"+search+"%",
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset())));
			
		} catch(SQLException sqle) {
			LOGGER.error("Tentative de requête: "+loggingQuery(query, "%"+search+"%",
					String.valueOf(Page.getNbrElements()),String.valueOf(page.getOffset()))+" échouée",sqle);
		}
		
		return resOrder;
		
	}
	
	private List<Computer> orderByName(String search, String direction, Page page){
		
		if(direction!=null && direction.equals("desc")) {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_COMPUTER_NAME_DESC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_COMPUTER_NAME_WITH_SEARCH_DESC.getQuery(), search);
			}
		}
		else {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_COMPUTER_NAME_ASC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_COMPUTER_NAME_WITH_SEARCH_ASC.getQuery(), search);
			}
		}
		
	}
	
	private List<Computer> orderByIntroduced(String search, String direction, Page page){
		
		if(direction!=null && direction.equals("desc")) {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_INTRODUCED_DESC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_INTRODUCED_WITH_SEARCH_DESC.getQuery(), search);
			}
		}
		else {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_INTRODUCED_ASC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_INTRODUCED_WITH_SEARCH_ASC.getQuery(), search);
			}
		}
		
	}
	
	private List<Computer> orderByDiscontinued(String search, String direction, Page page){
		
		if(direction!=null && direction.equals("desc")) {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_DISCONTINUED_DESC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_DISCONTINUED_WITH_SEARCH_DESC.getQuery(), search);
			}
		}
		else {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_DISCONTINUED_ASC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_DISCONTINUED_WITH_SEARCH_ASC.getQuery(), search);
			}
		}
		
	}
	
	private List<Computer> orderByCompany(String search, String direction, Page page){
		
		if(direction!=null && direction.equals("desc")) {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_COMPANY_NAME_DESC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_COMPANY_NAME_WITH_SEARCH_DESC.getQuery(), search);
			}
		}
		else {
			if(search==null || search.isEmpty()) {
				return orderByWithoutSearch(page, AllComputerQuery.ORDER_BY_COMPANY_NAME_ASC.getQuery());
			}
			else {
				return orderByWithSearch(page, AllComputerQuery.ORDER_BY_COMPANY_NAME_WITH_SEARCH_ASC.getQuery(), search);
			}
		}
		
	}

	
	public List<Computer> orderBy(String order, String search, String direction, Page page){
		
		List<Computer> res=new ArrayList<Computer>();
		
		switch (order) {
		
		case "name":
			res=orderByName(search, direction, page);
			break;
			
		case "introduced":
			res=orderByIntroduced(search, direction, page);
			break;
			
		case "discontinued":
			res=orderByDiscontinued(search, direction, page);
			break;
	
		case "company":
			res=orderByCompany(search, direction, page);
			break;
	
		default:
			
		}
		
		return res;
		
	}
	
}
