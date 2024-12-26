package com.csgp.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.csgp.backend.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
