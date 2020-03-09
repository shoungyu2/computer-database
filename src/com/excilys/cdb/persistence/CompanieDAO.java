package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Companie;

public class CompanieDAO {
	
	public static List<Companie> listCompanie(){
		DataBaseConnection dbc= DataBaseConnection.getDbCon();
		String selectComputer="SELECT * FROM computer";
		
		List<Companie> listComp=new ArrayList<>();
		
		try {
			ResultSet res=dbc.query(selectComputer);
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
	
}
