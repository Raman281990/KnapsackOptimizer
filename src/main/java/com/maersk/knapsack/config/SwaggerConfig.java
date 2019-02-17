package com.maersk.knapsack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/** SwaggerConfig configuration class for setting up swagger.
 * 
 *  Swagger provides the documentation details for the Rest API's
 * 
 * @author Ramandeep kaur
 *
 */
@Configuration
public class SwaggerConfig {


	@Bean public Docket api() { return new
			Docket(DocumentationType.SWAGGER_2).select()
			.apis(Predicates.not(RequestHandlerSelectors.basePackage(
					"org.springframework.boot")))
			.paths(Predicates.not(PathSelectors.regex("/error"))).build(); }

}
