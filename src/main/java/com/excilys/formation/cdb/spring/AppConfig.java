
package com.excilys.formation.cdb.spring;

import javax.servlet.ServletContext;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.cdb" })
@PropertySource(value = "classpath:application.properties")
public class AppConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("com.excilys.formation.cdb");
		context.close();
	}
	
	  @Autowired
	    private Environment env;

	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
	        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
	        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
	        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
	        return dataSource;
	    }

	    @Bean
	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        jdbcTemplate.setResultsMapCaseInsensitive(true);
	        return jdbcTemplate;
	}



}