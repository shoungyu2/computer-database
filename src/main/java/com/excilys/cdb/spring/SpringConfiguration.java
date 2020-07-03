package com.excilys.cdb.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.excilys.cdb.spring", "com.excilys.cdb.persistence","com.excilys.cdb.service","com.excilys.cdb.mapper","com.excilys.cdb.model"})
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
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean entityManager= new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(getHikariDataSource());
		entityManager.setPackagesToScan("com.excilys.cdb.persistence");
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		
		return entityManager;
		
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
	 
	    return transactionManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
}