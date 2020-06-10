package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyDAO {
	
	public final static	String SELECT_ALL_COMPANY="SELECT id,name FROM company"
			+ " ORDER BY id LIMIT ? OFFSET ?";
	public final static String SELECT_COMPANY="SELECT id,name FROM company WHERE id=?";
	
	public List<Company> listCompany(Page page){
				
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		
		List<Company> listComp=new ArrayList<>();
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_ALL_COMPANY)){
			
			pstmt.setInt(0, Page.getNbrElements());
			pstmt.setInt(1, page.getOffset());
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
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		
		try(PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPANY)){
			
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
	
}
