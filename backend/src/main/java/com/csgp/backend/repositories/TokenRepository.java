package com.csgp.backend.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csgp.backend.model.Token;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Integer> {


    @Query("""
    select t from Token t inner join User u on t.user.id = u.id
     where t.user.id = :userId and t.loggedOut = false""")
    List<Token> findAllAccessTokensByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);


    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.id = :userId")
    void deleteByUserId(Long userId);


}