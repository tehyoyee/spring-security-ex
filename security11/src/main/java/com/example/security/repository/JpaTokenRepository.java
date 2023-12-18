package com.example.security.repository;

import com.example.security.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaTokenRepository extends JpaRepository<Token, Integer> {

	Optional<Token> findTokenByIdentifier(String identifier);

}
