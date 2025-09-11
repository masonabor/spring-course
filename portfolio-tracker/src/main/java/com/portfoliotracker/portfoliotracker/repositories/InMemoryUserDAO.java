package com.portfoliotracker.portfoliotracker.repositories;

import com.portfoliotracker.portfoliotracker.models.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserDAO {
    private final Map<UUID, User> users = new HashMap<>();

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User findByEmail(String email) {
        return findAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User findById(Long id) {
        return users.get(id);
    }
}
