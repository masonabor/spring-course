package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.repositories.implementations.InMemoryUserDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final InMemoryUserDAO inMemoryUserDAO;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = inMemoryUserDAO.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        if (!user.isEnabled())
            throw new DisabledException("User not activated");

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                user.getAuthorities()
        );
    }
}
