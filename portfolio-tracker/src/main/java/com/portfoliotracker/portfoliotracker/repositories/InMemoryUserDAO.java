package com.portfoliotracker.portfoliotracker.repositories;

import com.portfoliotracker.portfoliotracker.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class InMemoryUserDAO {
    private final Map<UUID, User> users = new HashMap<>();

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addUser(User user) {
        var password = user.getPassword();
        user.setPassword(password);
        users.put(user.getId(), user);
    }

    public User findByEmail(String email) {
        return findAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User findById(UUID id) {
        return users.get(id);
    }

    public boolean existsById(UUID id) {
        return users.containsKey(id);
    }

    public boolean existsByUsername(String username) {
        return users.values()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean existsByEmail(String email) {
        return users.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public User findByUsername(String username) {
        return users.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}
