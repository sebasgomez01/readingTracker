package com.csgp.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csgp.backend.controllers.UserRestController;
import com.csgp.backend.controllers.BookRestController;

@SpringBootTest
@AutoConfigureMockMvc
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
        String userJson = "{\"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\"}";

        // Act 

        // Create User
		ResultActions result = mockMvc.perform(post("/login/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson));

        // Assert
        result.andExpect(status().isCreated());

        // Login
		result = mockMvc.perform(post("/login/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(header().exists("Authorization"))
            .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));
        
        String token = result.andReturn().getResponse().getHeader("Authorization");



    
        // chequeo que solamente devuelve los libros del usuario autenticado 
        MvcResult res = mockMvc.perform(get("/books")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn();    

                // Extraer el body de la respuesta
        String responseBody = res.getResponse().getContentAsString();

        // Assert
        System.out.println("HOLAAAAAAAAA. Response Body: " + responseBody); // Útil para depuración.

    }


}
