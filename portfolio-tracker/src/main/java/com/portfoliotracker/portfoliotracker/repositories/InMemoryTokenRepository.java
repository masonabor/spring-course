package com.portfoliotracker.portfoliotracker.repositories;

import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.models.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryTokenRepository {
    private final Map<User, VerificationToken> tokens = new HashMap<>();

    public void save(VerificationToken token) {
        this.tokens.put(token.getUser(), token);
    }
}
