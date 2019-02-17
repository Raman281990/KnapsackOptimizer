package com.maersk.knapsack.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** PersistenceJPAConfig Class initialises Entity Manager,Transaction Manager
 *  loads datasource and loads JPA entities
 *  
 *  
 * @author Ramandeep kaur
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.maersk.knapsack.repositories")
public class PersistenceJPAConfig {
	
	
	@Autowired
	MysqlProperties mysqlProperties;
	
	@Autowired
	HibernateProperties hibernateProperties;
	
	
	 
	   @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.maersk.knapsack.jpa.entities" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());
	 
	      return em;
	   }
	 
	   @Bean
	   public DataSource dataSource(){
	      DriverManagerDataSource dataSource = new DriverManagerDataSource();
	      dataSource.setDriverClassName(mysqlProperties.getDriverClassName());
	      dataSource.setUrl(mysqlProperties.getUrl());
	      dataSource.setUsername(mysqlProperties.getUser());
	      dataSource.setPassword(mysqlProperties.getPassword());
	      return dataSource;
	   }
	 
	   @Bean
	   public PlatformTransactionManager transactionManager(
	     EntityManagerFactory emf){
	       JpaTransactionManager transactionManager = new JpaTransactionManager();
	       transactionManager.setEntityManagerFactory(emf);
	 
	       return transactionManager;
	   }
	 
	   @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	       return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	   Properties additionalProperties() {
	       Properties properties = new Properties();
	       properties.setProperty(
	         "hibernate.dialect", hibernateProperties.getDialect());
	       properties.setProperty("hibernate.hbm2ddl.auto", "update");
	       return properties;
	   }
	  
}
