package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static synchronized DataBaseConnection getDbCon() {
		if (dbc==null) {
			dbc=new DataBaseConnection();
		}
		return dbc;
	}
	
	public PreparedStatement getPreparedStatement(String preparedQuery) throws SQLException{
		return conn.prepareStatement(preparedQuery);
	}
	
	public ResultSet query(String query) throws SQLException{
		st=dbc.conn.createStatement();
		ResultSet result=st.executeQuery(query);
		return result;
	}
	
	@Override
	public void finalize() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
