package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Companie;

/**
 * Classe implémentant les méthodes de manipulation des données sur les companies
 * @author masterchief
 */
public class CompanieDAO {
	
	/**
	 * Liste des requêtes nécessaire pour les méthodes ci-dessous
	 */
	public final static	String SELECT_COMPUTER="SELECT * FROM computer";

	
	/**
	 * Méthode retournant la liste de toutes les companies de la BDD
	 * @return une List<Companie> représentant la liste de toutes les companies
	 */
	public static List<Companie> listCompanie(){
		
		DataBaseConnection dbc= DataBaseConnection.getDbCon();
		
		List<Companie> listComp=new ArrayList<>();
		
		try {
			
			ResultSet res=dbc.query(SELECT_COMPUTER);
			
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
