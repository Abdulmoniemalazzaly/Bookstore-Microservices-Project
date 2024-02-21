package com.bookstore.auth.repo;

import com.bookstore.auth.model.jpa.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    @Query("""
        select t from Token t join User u on u.id = t.user.id
        where u.id =:userId and (t.expired = false and t.revoked = false )
    """)
    List<Token> findAllValidTokensByUser(@Param("userId") Long userId);

    @Query("""
        select new Token (t.revoked) from Token t where t.token =:token
    """)
    Optional<Token> findByToken(@Param("token") String token);
}
