package com.csgp.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.csgp.backend.controllers.UserRestController;

@ActiveProfiles("test") // esta anotación hizo que funcionen correctamente los tests 
@SpringBootTest
@AutoConfigureMockMvc
@Transactional //esto es para limpiar la base de datos después de cada test, igual los tests funcionan correctamente sin la anotación
public class UserRestControllerTests {
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRestController controller;

    

    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"cosme-fulanito\",\"password\":\"abcdef123456\"}";

		// Act
		// Create User
		ResultActions result = mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));
        // Assert
        result.andExpect(status().isCreated());
    }

    @Test
    public void testCreateUserWithExistsUsername() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"sam-gamyi\",\"password\":\"abcd1234\"}";

		// Act
		// Create User
		mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		ResultActions result = mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));
        
				// Assert
        result.andExpect(status().isConflict())
			.andExpect(content().string(org.hamcrest.Matchers.containsString("username is already taken!")));
    }


    @Test
    public void testLoginUser() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"frodo-baggins\",\"password\":\"1234abcd\"}";

		// Act

		// Create User
		mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/login/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson));

        String token = result.andReturn().getResponse().getHeader("Authorization");

        // Assert
        result.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));

        // chequeo que el token es válido
        mockMvc.perform(get("/books")
            .header("Authorization", token)) 
       .    andExpect(status().isOk());
    }


    @Test
    public void testLoginUserWithIncorrectUsername() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"frodo-baggins\",\"password\":\"1234abcd\"}";

		// Act

		// Create User
		mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/login/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"username\":\"frodo-bagin\",\"password\":\"1234abcd\"}"));

        // Assert
        result.andExpect(status().isForbidden());
			
    }

    @Test
    public void testLoginUserWithIncorrectPassword() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"legolas-greenleaf\",\"password\":\"abcd1234\"}";

		// Act
		// Create User
		mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/login/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"email\":\"legolas-greenleaf@gmail.com\",\"password\":\"abc1234\"}"));

        // Assert
        result.andExpect(status().isForbidden());
    }


    @Test
    public void testLoginNoExistingUser() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"gandalf-the-grey\",\"password\":\"youShallNotPass\"}";

		// Login
		ResultActions result = mockMvc.perform(post("/login/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson));

        // Assert
        result.andExpect(status().isForbidden());
    }

    @Test
    public void testLogoutUser() throws Exception {
        // Arrange
        String userJson = "{\"username\":\"frodo-baggins\",\"password\":\"1234abcd\"}";

		// Act

		// Create User
		mockMvc.perform(post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

		// Login
		ResultActions result = mockMvc.perform(post("/login/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson));

        String token = result.andReturn().getResponse().getHeader("Authorization");

        // Assert
        result.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")));

        // chequeo que el token es válido
        mockMvc.perform(get("/books")
            .header("Authorization", token)) 
            .andExpect(status().isOk());

        // logout
        mockMvc.perform(get("/logout")
            .header("Authorization", token)) 
            .andExpect(status().isOk());

        // chequeo que el token ya no es válido
        mockMvc.perform(get("/books")
            .header("Authorization", token)) 
            .andExpect(status().isForbidden());
        
    }    
}
