package br.com.lfmelo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisHandsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisHandsonApplication.class, args);
	}

}
