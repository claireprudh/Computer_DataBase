
package com.excilys.formation.cdb.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


@Configuration
@ComponentScan(basePackages = { 
		"com.excilys.formation.cdb.servlet",
		"com.excilys.formation.cdb.spring",
		"com.excilys.formation.cdb.tag"})
public class AppConfig {

		@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/classes/messages");
		return messageSource;
	}
	
}