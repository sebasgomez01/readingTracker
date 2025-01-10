package com.csgp.backend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.csgp.backend.model.User;
import com.csgp.backend.model.Book;
import com.csgp.backend.model.Token;
import com.csgp.backend.repositories.BookRepository;
import com.csgp.backend.repositories.UserRepository;
import com.csgp.backend.repositories.TokenRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.csgp.backend.security.JwtUtilities;

@DataJpaTest
@Import(JwtUtilities.class)
public class TokenRepositoryTests {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Test
    public void testFindAllAccesTokenByUser() {
        
        User user1 = new User("charly", "nadie-pudo-ver-que-el-tiempo-era-una-herida");
        User user2 = new User("luis-alberto", "toda-la-vida-tiene-musica-hoy"); 

        userRepository.save(user1);
        userRepository.save(user2);

        List<Token> tokensByUser1 = new LinkedList<>();
        List<Token> tokensByUser2 = new LinkedList<>();
        List<String> roles = Collections.singletonList("ROLE_USER");
        for(int i = 0; i < 5; i++) {
            Token token = new Token(jwtUtilities.generateToken(user1.getUsername(),roles), false, user1);
            tokensByUser1.add(token);
            tokenRepository.save(token);
            token = new Token(jwtUtilities.generateToken(user2.getUsername(),roles), false, user2);
            tokensByUser2.add(token);
            tokenRepository.save(token);
        }
        List<Token> tokenObtainedUser1 = tokenRepository.findAllAccessTokensByUser(user1.getId());
        assertTrue(tokenObtainedUser1.containsAll(tokensByUser1) && tokensByUser1.size() == tokenObtainedUser1.size());
        List<Token> tokenObtainedUser2 = tokenRepository.findAllAccessTokensByUser(user2.getId());
        assertTrue(tokenObtainedUser2.containsAll(tokensByUser2) && tokensByUser2.size() == tokenObtainedUser2.size());
    }

    public void testDeleteByUserId() {
        User user1 = new User("charly", "nadie-pudo-ver-que-el-tiempo-era-una-herida");
        User user2 = new User("luis-alberto", "toda-la-vida-tiene-musica-hoy"); 

        userRepository.save(user1);
        userRepository.save(user2);

        List<Token> tokensByUser1 = new LinkedList<>();
        List<Token> tokensByUser2 = new LinkedList<>();
        List<String> roles = Collections.singletonList("ROLE_USER");
        for(int i = 0; i < 5; i++) {
            Token token = new Token(jwtUtilities.generateToken(user1.getUsername(),roles), false, user1);
            tokensByUser1.add(token);
            tokenRepository.save(token);
            token = new Token(jwtUtilities.generateToken(user2.getUsername(),roles), false, user2);
            tokensByUser2.add(token);
            tokenRepository.save(token);
        }

        List<Token> tokenObtainedUser1 = tokenRepository.findAllAccessTokensByUser(user1.getId());
        assertEquals(tokenObtainedUser1.size(), 5);
        tokenRepository.deleteByUserId(user1.getId());
        tokenObtainedUser1 = tokenRepository.findAllAccessTokensByUser(user1.getId());
        assertEquals(tokenObtainedUser1.size(), 0);
    }
    

}
