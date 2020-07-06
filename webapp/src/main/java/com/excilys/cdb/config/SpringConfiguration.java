package com.excilys.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringDAOConfig.class, SpringServiceConfig.class, WebConfig.class, SpringBindingConfig.class, CDBWebApplicationInitializer.class})
public class SpringConfiguration {
	
}