package com.csgp.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csgp.backend.repositories.UserRepository;
import com.csgp.backend.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ActiveProfiles("test") parece ser que esta anotación no es necesaria en los tests de repositorios, por defecto se guardan en db en memoria
@DataJpaTest
public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Arrange
        User user1 = new User("charly", "nadie-pudo-ver-que-el-tiempo-era-una-herida");
        User user2 = new User("luis-alberto", "toda-la-vida-tiene-musica-hoy");
        
        userRepository.saveAll(List.of(user1, user2));

        // Act
        User userRepository1 = userRepository.findByUsername("charly").get();
        User userRepository2 = userRepository.findByUsername("luis-alberto").get();
        //User userRepository3 = userRepository.findByUsername("fito").get();

        // Assert
        assertEquals("charly", userRepository1.getUsername());
        assertEquals("luis-alberto", userRepository2.getUsername());
        //assertEquals(null, userRepository3);
    }

    @Test
    public void testExistsByUsername() {
        // Arrange
        User user = new User("frodo-baggins", "abcd1234");
        userRepository.save(user);

        // Act
        Boolean result1 = userRepository.existsByUsername("frodo-baggins");
        Boolean result2 = userRepository.existsByUsername("sam-gamyi");
        // Assert
        assertTrue(result1);
        assertFalse(result2);
    }  
}
