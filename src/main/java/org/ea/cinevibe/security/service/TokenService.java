package org.ea.cinevibe.security.service;

import org.ea.cinevibe.security.model.Token;
import org.ea.cinevibe.security.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getTokenByValue(String value){
        return tokenRepository.getTokenByValue(value).orElseThrow();
    }

    public void save(Token token){
        tokenRepository.save(token);
    }
}
