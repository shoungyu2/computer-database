package com.excilys.cdb.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringDAOConfig.class, SpringServiceConfig.class, WebConfig.class})
public class SpringConfiguration {
	
}