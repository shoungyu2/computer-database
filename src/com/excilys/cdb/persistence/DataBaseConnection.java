package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe pour créer la connection à la BDD
 * @author masterchief
 */
public class DataBaseConnection {
	
	private Connection conn;
	private Statement st;
	private static DataBaseConnection dbc;
	
	private DataBaseConnection() {
		String url="jdbc:mysql://localhost:3306/";
		String dbName="computer-database-db";
		String driver="com.mysql.cj.jdbc.Driver";
		String username="admincdb";
		String password="qwerty1234";
		try {
			Class.forName(driver);
			this.conn=(Connection)DriverManager.getConnection(url+dbName,username,password);
		}
		catch(Exception sqle) {
			sqle.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Crée la connection à la BSS
	 * @return dbc la connection à la BDD
	 */
	public static synchronized DataBaseConnection getDbCon() {
		if (dbc==null) {
			dbc=new DataBaseConnection();
		}
		return dbc;
	}
	
	public PreparedStatement getPreparedStatement(String preparedQuery) throws SQLException{
		return conn.prepareStatement(preparedQuery);
	}
	
	/**
	 * Exécute la requête passée en argument
	 * @param query une requête
	 * @return result le ResutSet généré par la requête
	 * @throws SQLException
	 */
	public ResultSet query(String query) throws SQLException{
		st=dbc.conn.createStatement();
		ResultSet result=st.executeQuery(query);
		return result;
	}
	
	/**
	 * Exécute la mise à jour passée en argument
	 * @param insertQuery une mise à jour
	 * @return result le résultat de la mise à jour (s'il y a eu une erreur ou non)
	 * @throws SQLException
	 */
	public int insert(String insertQuery) throws SQLException{
		st=dbc.conn.createStatement();
		int result=st.executeUpdate(insertQuery);
		return result;
	}
	
	@Override
	public void finalize() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
