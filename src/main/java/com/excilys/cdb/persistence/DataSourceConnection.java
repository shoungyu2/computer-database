package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class DataSourceConnection {

	@Autowired
	private static HikariDataSource hds;
	
	private DataSourceConnection() {}
	
	public static Connection getConnection() throws SQLException{
		return hds.getConnection();
	}
	
}
