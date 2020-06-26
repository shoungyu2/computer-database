package com.excilys.cdb.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.sevlet","com.excilys.cdb.persistence","com.excilys.cdb.service","com.excilys.cdb.mapper"})
public class SpringConfiguration {
	
	@Bean
	public HikariConfig hikariConfig() {
		return new HikariConfig("/hikari.properties");
	}
	
	@Bean
	public HikariDataSource getHikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public NamedParameterJdbcTemplate configJdbcTemplate(HikariDataSource hds) {
		
		return new NamedParameterJdbcTemplate(hds);
		
	}
	
}