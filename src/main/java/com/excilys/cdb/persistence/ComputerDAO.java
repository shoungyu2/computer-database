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

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;


public class ComputerDAO {
	
	private final static String INSERT_COMPUTER="INSERT INTO computer "
			+ "(name,introduced,discontinued, company_id) VALUES (?,?,?,?)";
	private final static String SELECT_ALL_COMPUTER=
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.id LIMIT ? OFFSET ?" ;
	private final static String SELECT_COMPUTER=
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.id=?";
	private final static String UPDATE_COMPUTER=
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private final static String DELETE_COMPUTER="DELETE FROM computer WHERE id=?";
	private final static String GET_NBR_COMPUTER="SELECT computer.id FROM computer"
			+ " ORDER BY computer.id DESC";
	private final static String SEARCH_COMPUTER=
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.id LIMIT ? OFFSET ?";
	private final static String GET_NBR_COMPUTER_IN_SEARCH=
			"SELECT COUNT(computer.id) FROM computer "
			+ " WHERE computer.name LIKE ? ";

	
	public int getNbrComputer() {
		
		try (Connection dbc=DataSourceConnection.getConnection()){
			ResultSet res=dbc.createStatement().executeQuery(GET_NBR_COMPUTER);
			if(res.next()) {
				return res.getInt("computer.id");
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
		
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
				ldt==null ? 
				null : Timestamp.valueOf(ldt);
		return date;
		
	}
	
	private int getCompanieIDFromComputer(Computer c) {
		
		return c.getCompany()==null?0:c.getCompany().getId();
		
	}
	
	public List<Computer> listComputer(Page page){
		
		List<Computer> listComp=new ArrayList<>();
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(SELECT_ALL_COMPUTER)
			){
			pstmt.setInt(1, Page.getNbrElements());
			pstmt.setInt(2, page.getOffset());
			ResultSet res=pstmt.executeQuery();
			while(res.next()) {
				Computer c=createComputerFromBDD(res);
				listComp.add(c);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComp;
	}
	
	public Optional<Computer> showDetailComputer(int id) {
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(SELECT_COMPUTER)
			){
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
	
	public void createComputer(Computer c) {
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(INSERT_COMPUTER)
			){
			pstmt.setString(1, c.getName());
			Timestamp introDate=getDateFromComputer(c.getIntroductDate());
			pstmt.setTimestamp(2, introDate);
			Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
			pstmt.setTimestamp(3, discDate);			
			int companyID=getCompanieIDFromComputer(c);
			if(companyID==0) {
				pstmt.setNull(4, Types.BIGINT);
			}
			else {
				pstmt.setInt(4, companyID);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int max=getNbrComputer()/Page.getNbrElements()+1;
		if(max>Page.getNbrPages()) {
			Page.setNbrPages(max);
		}
		
	}
	
	public void updateComputer(Computer c) {
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(UPDATE_COMPUTER)
			){			
			pstmt.setInt(5, c.getId());
			pstmt.setString(1, c.getName());
			Timestamp introDate=getDateFromComputer(c.getIntroductDate());
			pstmt.setTimestamp(2, introDate);
			Timestamp discDate=getDateFromComputer(c.getDiscontinueDate());
			pstmt.setTimestamp(3, discDate);
			int companyID=getCompanieIDFromComputer(c);
			if(companyID==0) {
				pstmt.setNull(4, Types.BIGINT);
			}
			else {
				pstmt.setInt(4, companyID);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteComputer(int id) {
		
		try (
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(DELETE_COMPUTER)
			){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int min=getNbrComputer()/Page.getNbrElements()+1;
		if(min<Page.getNbrPages()) {
			Page.setNbrPages(min);
		}
		
	}
	
	public int getNbrComputerInSearch(String search) {
		
		try(
				Connection dbc= DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(GET_NBR_COMPUTER_IN_SEARCH);
			){

			search=search.replace("%", "\\%");
			pstmt.setString(1, "%"+search+"%");
			ResultSet res= pstmt.executeQuery();
			if(res.next()) {
				return res.getInt(1);
			}
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return 0;
		
	}
	
	public List<Computer> searchComputer(String search, Page page){
		
		List<Computer> searchResult=new ArrayList<Computer>();
		
		try(
				Connection dbc=DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(SEARCH_COMPUTER);
			){
			
			search=search.replace("%", "\\%");
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, Page.getNbrElements());
			pstmt.setInt(3, page.getOffset());
			ResultSet res=pstmt.executeQuery();
			while(res.next()) {
				Computer c=createComputerFromBDD(res);
				searchResult.add(c);
			}
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return searchResult;
		
	}
	
}
