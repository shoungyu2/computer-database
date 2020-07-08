package com.excilys.cdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(){
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(User.builder().passwordEncoder(passwordEncoder::encode)
				.username("shoungyu2").password("qwerty1234").roles("USER","ADMIN").build());

		return inMemoryUserDetailsManager;
		
	}
	
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.authorizeRequests()
		.antMatchers("/AddComputerController","EditComputerController")
		.hasRole("ADMIN").and().formLogin()
		.and().httpBasic().and().logout().logoutUrl("logout").logoutSuccessUrl("/");
		
	}
	
}
