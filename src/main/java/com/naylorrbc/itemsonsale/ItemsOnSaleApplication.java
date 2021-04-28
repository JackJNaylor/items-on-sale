package com.naylorrbc.itemsonsale;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Items On Sale", version = "1.0", description = "Microservice to provide recommended items on sale to customer"))
public class ItemsOnSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsOnSaleApplication.class, args);
	}

}
