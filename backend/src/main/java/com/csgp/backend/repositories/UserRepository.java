package com.csgp.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import com.csgp.backend.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long>{
    
}
