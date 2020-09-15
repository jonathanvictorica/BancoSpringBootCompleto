package com.jmg.banco.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("com.jmg.banco.controller"))
				.paths(PathSelectors.any())				
				.build().apiInfo(apiInfo());
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
		"API Banco JMG",
		"API Operaciones Basicas",
		"1.0",
		"urn:tos",
		new Contact("Jonathan Victorica", null, "jonathan.m.victorica@gmail.com"),
		null,
		null);
		}

}