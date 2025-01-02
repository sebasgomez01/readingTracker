package com.csgp.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.csgp.backend.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
    @RestResource(exported = false) // Deshabilita la exposición automática de findAll
    @Override
    List<Book> findAll();

    // Devuelve los libros asociados a un usuario específico
    List<Book> findByUserUsername(String username);
}
