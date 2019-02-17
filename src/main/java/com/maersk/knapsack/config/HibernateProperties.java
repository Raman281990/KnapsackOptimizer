package com.maersk.knapsack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:persistence-mysql.properties"})
@ConfigurationProperties(prefix = "hibernate")
public class HibernateProperties {

	private String dialect;
	
	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}


}
