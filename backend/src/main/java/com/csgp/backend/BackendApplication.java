package com.csgp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.csgp.backend.model.Book;

@SpringBootApplication
public class BackendApplication implements RepositoryRestConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// para devolver el id del libro en response de cada petición
	@Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Book.class);
    }
}
