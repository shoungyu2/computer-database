package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public class CompanyDAO {
	
	public final static	String SELECT_ALL_COMPANY="SELECT id,name FROM company"
			+ " ORDER BY id";
	public final static String SELECT_COMPANY="SELECT id,name FROM company WHERE id=?";
	public final static String DELETE_COMPANY="DELETE FROM company WHERE id=?";
	public final static String DELETE_ALL_COMPUTER_WITH_COMPANY="DELETE FROM computer WHERE company_id=?";
	
	public List<Company> listCompany(){
		
		List<Company> listComp=new ArrayList<>();
		
		try (
				Connection dbc= DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(SELECT_ALL_COMPANY)
			){
			
			ResultSet res=pstmt.executeQuery();
			
			while(res.next()) {
				Company c;
				String name=res.getString("name");
				int id=res.getInt("id");
				c=new Company(name,id);
				listComp.add(c);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		return listComp;
	
	}
	
	public Optional<Company> showDetailCompany(int id) {
		
		try(
				Connection dbc= DataSourceConnection.getConnection();
				PreparedStatement pstmt=dbc.prepareStatement(SELECT_COMPANY)
			){
			
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			Company c;
			if(res.next()) {
				int idComp=res.getInt("id");
				String name=res.getString("name");
				c=new Company(name,idComp);
				return Optional.of(c);
			}
						
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public void deleteCompany(int id) {
		
		Connection dbc=null;
		try{

			dbc = DataSourceConnection.getConnection();
			PreparedStatement pstmt_del_company=dbc.prepareStatement(DELETE_COMPANY);
			PreparedStatement pstmt_del_computer=dbc.prepareStatement(DELETE_ALL_COMPUTER_WITH_COMPANY);
					
			dbc.setAutoCommit(false);
			pstmt_del_computer.setInt(1, id);
			pstmt_del_company.setInt(1, id);
			pstmt_del_computer.executeUpdate();
			pstmt_del_company.executeUpdate();
			dbc.commit();
			dbc.setAutoCommit(true);
			
		} catch(SQLException sqle) {
			try {
				dbc.rollback();
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
