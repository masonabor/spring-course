package com.portfoliotracker.portfoliotracker.repositories.implementations;

import com.portfoliotracker.portfoliotracker.models.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryTokenRepository {
    private final Map<String, VerificationToken> tokens = new HashMap<>();

    public void save(VerificationToken token) {
        this.tokens.put(token.getToken(), token);
    }

    public VerificationToken getVerificationToken(String token) {
        return this.tokens.get(token);

    }
}
