package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.repositories.InMemoryUserDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private InMemoryUserDAO inMemoryUserDAO;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = inMemoryUserDAO.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Немає користувача з таким юзернеймом: " + email);
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, user.getAuthorities());
    }
}
