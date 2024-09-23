package com.dev.ad_marvel_hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class AdMarvelHeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdMarvelHeroApplication.class, args);
	}

	@Bean
	public RestClient getRestClient(){
		return RestClient.create();
	}

}
