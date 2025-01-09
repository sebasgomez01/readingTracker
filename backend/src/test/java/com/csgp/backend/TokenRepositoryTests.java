package com.csgp.backend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csgp.backend.model.User;
import com.csgp.backend.model.Book;
import com.csgp.backend.model.Token;
import com.csgp.backend.repositories.BookRepository;
import com.csgp.backend.repositories.UserRepository;
import com.csgp.backend.repositories.TokenRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class TokenRepositoryTests {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    User user1 = new User("charly", "nadie-pudo-ver-que-el-tiempo-era-una-herida");
    User user2 = new User("luis-alberto", "toda-la-vida-tiene-musica-hoy"); 

    userRepository.save(user1);
    userRepository.save(user2);

    //Token token1 = new Token();

}
