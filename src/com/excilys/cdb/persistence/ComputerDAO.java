package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Companie;
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
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id" ;
	private final static String SELECT_COMPUTER=
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.id=?";
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
				
				String name=res.getString("computer.name");
				int id=res.getInt("computer.id");
								
				Timestamp introDate=res.getTimestamp("introduced");
				LocalDateTime introLDT=null;
				if(introDate!=null) {
					introLDT=introDate.toLocalDateTime();
				}
				
				Timestamp discDate=res.getTimestamp("discontinued");
				LocalDateTime discLDT=null;
				if(discDate!=null) {
					discLDT=discDate.toLocalDateTime();
				}
				
				int compID=res.getInt("company_id");
				Companie company=
						compID==0?
						null:new Companie(res.getString("company.name"),compID);

				c=new Computer.ComputerBuilder(name,id)
						.setIntroductDate(introLDT)
						.setDiscontinueDate(discLDT)
						.setEntreprise(company)
						.build();

				listComp.add(c);
			
			}	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listComp;
	}
	
	/**
	 * Affiche en console les détails de l'ordinateur dont l'ID est le paramètre donné
	 * @param id un entier
	 * @return un Optional<Computer>
	 */
	public static Optional<Computer> showDetailComputer(int id) {
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(SELECT_COMPUTER)){
			
			pstmt.setInt(1, id);
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				
				Computer c;
				
				String name=res.getString("computer.name");
				int idComputer=res.getInt("computer.id");
				
				Timestamp introDate=res.getTimestamp("introduced");
				LocalDateTime introLDT=
						introDate==null ?
						null:introDate.toLocalDateTime();
				
				Timestamp discDate=res.getTimestamp("discontinued");
				LocalDateTime discLDT=
						discDate==null ?
						null:discDate.toLocalDateTime();
				
				int idCompanie=res.getInt("company_id");
				Companie company=
						idCompanie==0?
						null:new Companie(res.getString("company.name"),idCompanie);

				c=new Computer.ComputerBuilder(name,idComputer)
						.setIntroductDate(introLDT)
						.setDiscontinueDate(discLDT)
						.setEntreprise(company)
						.build();
				
				return Optional.of(c);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.empty();
		
	}
	
	/**
	 * Méthode permettant d'ajouter un nouvel ordinateur dans la BDD
	 * @param c un ordinateur
	 */
	public static void createComputer(Computer c) {
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(INSERT_COMPUTER)){
			
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
			
			int companyID=c.getEntreprise()==null?0:c.getEntreprise().getId();
			pstmt.setInt(5, companyID);
			
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
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(UPDATE_COMPUTER)){			
			
			pstmt.setInt(4, c.getID());
			
			Timestamp introDate=
					c.getIntroductDate()==null ? 
					null : Timestamp.valueOf(c.getIntroductDate());
			pstmt.setTimestamp(1, introDate);
			
			Timestamp discDate=
					c.getDiscontinueDate()==null ? 
					null : Timestamp.valueOf(c.getDiscontinueDate());
			pstmt.setTimestamp(2, discDate);
			
			int companyID=
					c.getEntreprise()==null ?
					0:c.getEntreprise().getId();
			pstmt.setInt(3, companyID);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Méthode permettant de supprimer un ordinateur de la base de donnée grâce à son id
	 * @param id un entier
	 */
	public static void deleteComputer(int id) {
		
		try (PreparedStatement pstmt=dbc.getPreparedStatement(DELETE_COMPUTER)){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
