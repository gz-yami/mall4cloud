package com.mall4j.cloud.gateway.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger 3.0 展示
 */
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration {

	private final DiscoveryClient discoveryClient;

	public SpringDocConfiguration(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Bean
	@Lazy(false)
	@ConditionalOnProperty(name = "springdoc.api-docs.enabled", matchIfMissing = true)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters) {
		List<GroupedOpenApi> groups = new ArrayList<>();

		for (String serviceId : discoveryClient.getServices()) {
			swaggerUiConfigParameters.addGroup(serviceId, serviceId);
		}
		return groups;
	}
}
