package com.socialbooks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("alexandre")
			.password("s3nh4")
			.roles("USER");
			//Basic YWxleGFuZHJlOnMzbmg0
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll() // Desabilita a autenticação desta URL
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
	}
	
}
