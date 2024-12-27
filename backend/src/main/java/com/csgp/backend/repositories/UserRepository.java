package com.csgp.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import com.csgp.backend.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
