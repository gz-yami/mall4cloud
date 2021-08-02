package com.mall4j.cloud.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * @author YXF
 * @date 2021-01-27 09:10:00
 */
@Component
@Primary
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

	private static final Logger log = LoggerFactory.getLogger(SwaggerResourceConfig.class);

	private final RouteLocator routeLocator;

	private final GatewayProperties gatewayProperties;

	public SwaggerResourceConfig(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
		this.routeLocator = routeLocator;
		this.gatewayProperties = gatewayProperties;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<String> routes = new ArrayList<>();
		routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
		for (RouteDefinition routeDefinition : gatewayProperties.getRoutes()) {
			if (routes.contains(routeDefinition.getId())) {
				for (PredicateDefinition predicateDefinition : routeDefinition.getPredicates()) {
					if (("Path").equalsIgnoreCase(predicateDefinition.getName())) {
						resources
								.add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
										.get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", "v2/api-docs")));
					}
				}
			}
		}

		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		log.info("name:{},location:{}", name, location);
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

}
