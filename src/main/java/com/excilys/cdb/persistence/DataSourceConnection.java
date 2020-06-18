package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceConnection {

	private static HikariConfig hConfig=new HikariConfig("/hikari.properties");
	private static HikariDataSource hds;
	
	static {
		hds= new HikariDataSource(hConfig);
	}
	
	private DataSourceConnection() {}
	
	public static Connection getConnection() throws SQLException{
		return hds.getConnection();
		
	}
	
}
