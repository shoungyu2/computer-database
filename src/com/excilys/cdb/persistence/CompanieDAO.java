package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Companie;

public class CompanieDAO {
	
	public final static	String SELECT_ALL_COMPANIE="SELECT id,name FROM company";
	public final static String SELECT_COMPANIE="SELECT id,name FROM company WHERE id=?";
	
	public static List<Companie> listCompanie(){
				
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		
		List<Companie> listComp=new ArrayList<>();
		
		try {
			
			ResultSet res=dbc.query(SELECT_ALL_COMPANIE);
			
			while(res.next()) {
				Companie c;
				String name=res.getString("name");
				int id=res.getInt("id");
				c=new Companie(name,id);
				listComp.add(c);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		return listComp;
	
	}
	
	public static Optional<Companie> showDetailCompanie(int id) {
		
		DataBaseConnection dbc=DataBaseConnection.getDbCon();
		
		try(PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPANIE)){
			
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			Companie c;
			if(res.next()) {
				int idComp=res.getInt("id");
				String name=res.getString("name");
				c=new Companie(name,idComp);
				return Optional.of(c);
			}
						
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return Optional.empty();
	}
	
}
