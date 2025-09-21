package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.repositories.InMemoryUserDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private InMemoryUserDAO inMemoryUserDAO;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        try {
            User user = inMemoryUserDAO.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Немає користувача з таким юзернеймом: " + username);
            } else if (!user.isActivated()) {
                throw new DisabledException("Користувач не активований: " + username);
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    user.getAuthorities()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
