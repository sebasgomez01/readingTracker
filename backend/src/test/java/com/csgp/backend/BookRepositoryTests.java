package com.csgp.backend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csgp.backend.model.User;
import com.csgp.backend.model.Book;
import com.csgp.backend.repositories.BookRepository;
import com.csgp.backend.repositories.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class BookRepositoryTests {
    
    @Autowired
    private BookRepository bookRepository;    

    @Autowired
    private UserRepository userRepository;    

    @Test 
    public void testFindByUserUsername() {
        // Arrange
        User user1 = new User("charly", "nadie-pudo-ver-que-el-tiempo-era-una-herida");
        User user2 = new User("luis-alberto", "toda-la-vida-tiene-musica-hoy"); 

        userRepository.save(user1);
        userRepository.save(user2);

        Book book1 = new Book("El Silmarillion", "J. R. R. Tolkien", 587, true, "09/01/2025", user1);
        Book book2 = new Book("La comunidad del Anillo", "J. R. R. Tolkien", 587, true, "09/01/2025", user1);
        Book book3 = new Book("Las dos torres", "J. R. R. Tolkien", 587, true, "09/01/2025", user2);
        Book book4 = new Book("El retorno del rey", "J. R. R. Tolkien", 587, true, "09/01/2025", user2);
        
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        // Act
        List<Book> booksByUser1 = (List<Book>) bookRepository.findByUserUsername(user1.getUsername());
        List<Book> booksByUser2 = (List<Book>) bookRepository.findByUserUsername(user2.getUsername());

        assertEquals(2, booksByUser1.size());
        assertEquals(2, booksByUser2.size());
        assertTrue(booksByUser1.contains(book1));
        assertTrue(booksByUser1.contains(book2));
        assertTrue(booksByUser2.contains(book3));
        assertTrue(booksByUser2.contains(book4));
    }

}
