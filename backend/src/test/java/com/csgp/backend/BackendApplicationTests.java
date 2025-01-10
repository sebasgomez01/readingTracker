package com.csgp.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // esta anotación hizo que funcionen correctamente los tests cambiando el perfil a test para usar la base de datos en memoria 
@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
