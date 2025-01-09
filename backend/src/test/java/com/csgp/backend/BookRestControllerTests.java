package com.csgp.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.csgp.backend.controllers.UserRestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.transaction.Transactional;

import com.csgp.backend.controllers.BookRestController;

@ActiveProfiles("test") // //ta anotación hizo que funcionen correctamente los tests ya que cambia el perfil a test para usar la db h2 en memoria
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // cuando tengo más de una clase de tests esta anotación es necesaria para limpiar la base de datos luego de cada test
public class BookRestControllerTests {

    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRestController userController;

    @Autowired
    private BookRestController bookController;

    @Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
        assertThat(bookController).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

    @Test 
    public void testReturnOnlyBooksOfAuthenticatedUser() throws Exception {
        // Arrange
        String userJson1 = "{\"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\"}";
        String userJson2 = "{\"username\":\"lalo-landa\",\"password\":\"1234abcd\"}";

        // Act 

        // Create Users
		ResultActions result = mockMvc.perform(post("/login/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson1));

        // Assert
        result.andExpect(status().isCreated());

        result = mockMvc.perform(post("/login/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson2));

        // Assert
        result.andExpect(status().isCreated());

        // Login
		result = mockMvc.perform(post("/login/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson1));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(header().exists("Authorization"))
            .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));
        
        String token = result.andReturn().getResponse().getHeader("Authorization");

        String book1 = "{\"title\":\"El Silmarillion\",\"author\":\"J. R. R. Tolkien\",\"pages\": 587, \"read\": true, \"savedDate\": \"09/01/2025\"}";
        String book2 = "{\"title\":\"La comunidad del Anillo\",\"author\":\"J. R. R. Tolkien\",\"pages\": 587, \"read\": true, \"savedDate\": \"09/01/2025\"}";
        String book3 = "{\"title\":\"Las dos torres\",\"author\":\"J. R. R. Tolkien\",\"pages\": 587, \"read\": true, \"savedDate\": \"09/01/2025\"}";
        String book4 = "{\"title\":\"El retorno del rey\",\"author\":\"J. R. R. Tolkien\",\"pages\": 587, \"read\": true, \"savedDate\": \"09/01/2025\"}";

        // guardo un par de libros 
        result = mockMvc.perform(post("/books")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(book1));

        result.andExpect(status().isCreated());

        result = mockMvc.perform(post("/books")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(book2));

        result.andExpect(status().isCreated());
    
        // cierro la sesión 
        result = mockMvc.perform(get("/logout")
                    .header("Authorization", token));

        result.andExpect(status().isOk());

        // inicio sesión con el usuario 2
        result = mockMvc.perform(post("/login/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson2));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(header().exists("Authorization"))
            .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));
        
        token = result.andReturn().getResponse().getHeader("Authorization");

        // guardo un par de libros 
        result = mockMvc.perform(post("/books")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(book3));

        result.andExpect(status().isCreated());

        result = mockMvc.perform(post("/books")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(book4));

        result.andExpect(status().isCreated());

        // chequeo que solamente devuelve los libros del usuario autenticado 
        MvcResult res = mockMvc.perform(get("/books")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn();    

        // Extraer el body de la respuesta
        String responseBody = res.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> books = objectMapper.readValue(responseBody, new TypeReference<>() {});

        // Verifico que la lista tiene exactamente 2 libros
        assertThat(books).hasSize(2);

        List<String> expectedTitles = List.of("Las dos torres", "El retorno del rey");

        // Extraigo los títulos de los libros de la respuesta
        List<String> actualTitles = books.stream()
                .map(book -> (String) book.get("title"))
                .collect(Collectors.toList());

        // Assert
        // Verifico que los títulos coinciden con los esperados
        assertThat(actualTitles).containsExactlyInAnyOrderElementsOf(expectedTitles);


    }


}
