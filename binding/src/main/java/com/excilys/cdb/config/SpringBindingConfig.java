package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages= {"com.excilys.cdb.mapper","com.excilys.cdb.validator","com.excilys.cdb.dto"})
public class SpringBindingConfig {	
}