package com.example.security.csrf;

import com.example.security.entity.Token;
import com.example.security.repository.JpaTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import java.util.Optional;
import java.util.UUID;

public class CustomCsrfTokenRepository implements CsrfTokenRepository {

	@Autowired
	private JpaTokenRepository jpaTokenRepository;

	@Override
	public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
		String uuid = UUID.randomUUID().toString();
		return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

		if (existingToken.isPresent()) {
			Token token = existingToken.get();
			token.setToken(csrfToken.getToken());
		} else {
			Token token = new Token();
			token.setToken(csrfToken.getToken());
			token.setIdentifier(identifier);
			jpaTokenRepository.save(token);
		}
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
		String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

		if (existingToken.isPresent()) {
			Token token = existingToken.get();
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
		}

		return null;
	}
}