package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;

/**
 * Classe implémentant les méthodes de manipulation des données concernant les ordinateurs
 * @author masterchief
 */
public class ComputerDAO {
	
	/**
	 * Toutes les requêtes nécessaires pour les méthodes ci-dessous
	 */
	private final static String INSERT_COMPUTER="INSERT INTO computer VALUES(?,?,?,?,?)";
	private final static String SELECT_ALL_COMPUTER=
			"SELECT id,name,introduced,discontinued,company_id FROM computer";
	private final static String SELECT_COMPUTER_BY_ID=
			"SELECT * FROM computer where id=?";
	private final static String SELECT_COMPUTER_BY_NAME=
			"SELECT * FROM computer where name=?";
	private final static String UPDATE_COMPUTER=
			"UPDATE computer SET introduced=?, discontinued=?, company_id=? WHERE id=?";
	private final static String DELETE_COMPUTER="DELETE FROM computer WHERE id=?";
	
	
	/**
	 * La connection à la BDD
	 */
	private static DataBaseConnection dbc;
	
	
	public ComputerDAO() {
		dbc=DataBaseConnection.getDbCon();
	}
	
	/**
	 * Liste tous les ordinateurs de la BDD
	 * @return listComp la liste de tous les ordinateurs de la BDD
	 */
	public static List<Computer> listComputer(){
		
		List<Computer> listComp=new ArrayList<>();
		
		try {
			ResultSet res=dbc.query(SELECT_ALL_COMPUTER);
			while(res.next()) {
				
				Computer c;
				
				String name=res.getString("name");
				int id=res.getInt("id");
				
				c=new Computer.ComputerBuilder(name, id).build();
				
				Timestamp introDate=res.getTimestamp("introduced");
				if(introDate!=null) {
					LocalDateTime introLDT=introDate.toLocalDateTime();
					c.setIntroductDate(introLDT);
				}
				
				Timestamp discDate=res.getTimestamp("discontinued");
				if(discDate!=null) {
					LocalDateTime discLDT=discDate.toLocalDateTime();
					c.setDiscontinueDate(discLDT);
				}
				
				int compID=res.getInt("company_id");
				c.setIDEntreprise(compID);
				
				listComp.add(c);
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		return listComp;
	}
	
	/**
	 * Affiche en console les détails de l'ordinateur dont l'ID est le paramètre donné
	 * @param id un entier
	 * @return un ordinateur si l'id fournit était présent dans la base
	 */
	public static Optional<Computer> showDetailComputerByID(int id) {
		
		try {
			
			PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPUTER_BY_ID);
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				
				Computer c;
				
				String name=res.getString("name");
				int idComputer=res.getInt("id");
				
				c=new Computer.ComputerBuilder(name, idComputer).build();
				
				Timestamp introDate=res.getTimestamp("introduced");
				if(introDate!=null) {
					LocalDateTime introLDT=introDate.toLocalDateTime();
					c.setIntroductDate(introLDT);
				}
				
				Timestamp discDate=res.getTimestamp("discontinued");
				if(discDate!=null) {
					LocalDateTime discLDT=discDate.toLocalDateTime();
					c.setDiscontinueDate(discLDT);
				}
				
				int idCompanie=res.getInt("company_id");
				c.setIDEntreprise(idCompanie);
				
				System.out.println(c);
				
				return Optional.of(c);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.empty();
		
	}
	
	/**
	 * Affiche en console les détails de l'ordinateur dont le nom est le paramètre donné
	 * @param name une chaîne de caractère
	 * @return un ordinateur si le nom fournit était dans la BDD
	 */
	public static Optional<Computer> showDetailComputerByName(String name) {
		
		try {
			
			PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPUTER_BY_NAME);
			pstmt.setString(1, name);
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				
				Computer c;
				
				String nameComp=res.getString("name");
				int idComputer=res.getInt("id");
				
				c=new Computer.ComputerBuilder(nameComp, idComputer).build();
				
				Timestamp introDate=res.getTimestamp("introduced");
				if(introDate!=null) {
					LocalDateTime introLDT=introDate.toLocalDateTime();
					c.setIntroductDate(introLDT);
				}
				
				Timestamp discDate=res.getTimestamp("discontinued");
				if(discDate!=null) {
					LocalDateTime discLDT=discDate.toLocalDateTime();
					c.setDiscontinueDate(discLDT);
				}
				
				int idCompanie=res.getInt("company_id");
				c.setIDEntreprise(idCompanie);
				
				System.out.println(c);
				
				return Optional.of(c);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.empty();
		
	}
	
	
	/**
	 * Méthode permettant d'ajouter un nouvel ordianateur dans la BDD
	 * @param c un ordinateur
	 */
	public static void createComputer(Computer c) {
		
		try {
			
			PreparedStatement pstmt=dbc.getPreparedStatement(INSERT_COMPUTER);
			pstmt.setInt(1, c.getID());
			pstmt.setString(2, c.getName());
			
			Timestamp introDate=
					c.getIntroductDate()==null ? 
							null : Timestamp.valueOf(c.getIntroductDate());
			pstmt.setTimestamp(3, introDate);
			
			Timestamp discDate=
					c.getDiscontinueDate()==null ? 
							null : Timestamp.valueOf(c.getDiscontinueDate());
			pstmt.setTimestamp(4, discDate);			
			
			pstmt.setInt(5, c.getIDEntreprise());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Méthode permettant la mise à jour des données d'un ordianateur dans la BDD
	 * @param c un ordinateur
	 */
	public static void updateComputer(Computer c) {
		
		try {
			
			PreparedStatement pstmt=dbc.getPreparedStatement(UPDATE_COMPUTER);
			
			pstmt.setInt(4, c.getID());
			
			Timestamp introDate=
					c.getIntroductDate()==null ? 
							null : Timestamp.valueOf(c.getIntroductDate());
			pstmt.setTimestamp(1, introDate);
			
			Timestamp discDate=
					c.getDiscontinueDate()==null ? 
							null : Timestamp.valueOf(c.getDiscontinueDate());
			pstmt.setTimestamp(2, discDate);
			
			pstmt.setInt(3, c.getIDEntreprise());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteComputer(int id) {
		
		try {
			
			PreparedStatement pstmt=dbc.getPreparedStatement(DELETE_COMPUTER);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
