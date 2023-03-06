package com.mall4j.cloud.leaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author leaf
 */
@SpringBootApplication(scanBasePackages = { "com.mall4j.cloud" })
@EnableFeignClients(basePackages = {"com.mall4j.cloud.api.**.feign"})
public class LeafServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeafServerApplication.class, args);
	}

}
