package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.cdb.spring.SpringConfiguration;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceConnection {

	private static HikariDataSource hds;
	
	static {
		hds= SpringConfiguration.getContext().getBean(HikariDataSource.class);
	}
	
	private DataSourceConnection() {}
	
	public static Connection getConnection() throws SQLException{
		return hds.getConnection();
	}
	
}
