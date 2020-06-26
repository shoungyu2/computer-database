package com.excilys.cdb.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CDBWebApplicationInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) {
		
		AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
		annotationConfigWebApplicationContext.register(SpringConfiguration.class);
		annotationConfigWebApplicationContext.refresh();
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(annotationConfigWebApplicationContext);
		ServletRegistration.Dynamic registration = servletContext.addServlet("computer-database", dispatcherServlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/computer-database/");
		
	}
	
}
