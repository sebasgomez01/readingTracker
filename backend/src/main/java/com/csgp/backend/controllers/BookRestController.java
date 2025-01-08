package com.csgp.backend.controllers;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csgp.backend.model.Book;
import com.csgp.backend.repositories.BookRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RepositoryRestController
//@RequestMapping("/books") si estaba esta anotación no compilaba 
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /*  
        este método sirve para devolver solamente los libros del usuario autenticado en el momento
        y no todos los libros como lo hace Spring Data Rest por defecto
    */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooksForAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            List<Book> books = bookRepository.findByUserUsername(username);
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.ok(List.of()); // Retorna lista vacía si no hay usuario autenticado
    }
}

