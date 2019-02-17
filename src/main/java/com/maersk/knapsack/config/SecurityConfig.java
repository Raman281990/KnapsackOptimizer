package com.maersk.knapsack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Authentication : User --> Roles
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ACTUATOR");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
		 .anyRequest().authenticated()
		 .and().httpBasic()
		 .authenticationEntryPoint(getBasicAuthEntryPoint());
		 }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("swagger-ui.htm").antMatchers(HttpMethod.GET);
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}

}