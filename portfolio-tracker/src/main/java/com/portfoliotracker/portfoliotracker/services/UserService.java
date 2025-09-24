package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.exceptions.UserAlreadyExistsException;
import com.portfoliotracker.portfoliotracker.exceptions.UserRegistrationException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.models.VerificationToken;
import com.portfoliotracker.portfoliotracker.repositories.implementations.InMemoryTokenRepository;
import com.portfoliotracker.portfoliotracker.repositories.implementations.InMemoryUserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final InMemoryUserDAO inMemoryUserDAO;
    private final InMemoryTokenRepository inMemoryTokenDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(String email) {
        return inMemoryUserDAO.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return inMemoryUserDAO.existsByUsername(username);
    }

    @Override
    public boolean existsUser(UserRegistrationDTO userDTO) throws UserAlreadyExistsException {
        boolean existsByUsername = inMemoryUserDAO.existsByUsername(userDTO.getUsername());
        boolean existsByEmail = inMemoryUserDAO.existsByEmail(userDTO.getEmail());
        if (existsByUsername) {
            throw new UserAlreadyExistsException("registration.existsByUsername");
        } else if (existsByEmail) {
            throw new UserAlreadyExistsException("registration.existsByEmail");
        } else if (existsByUsername && existsByEmail) {
            throw new UserAlreadyExistsException("registration.existsByUsernameAndEmail");
        } else return false;
    }

    @Override
    public void saveRegisteredUser(User user) {

    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = VerificationToken.of(token, user);
        inMemoryTokenDAO.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return inMemoryTokenDAO.getVerificationToken(token);
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDTO userDTO) throws UserRegistrationException {
        try {
            boolean existsUser = existsUser(userDTO);
            if (!existsUser) {
                var user = User.of(userDTO);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                inMemoryUserDAO.addUser(user);
                return user;
            }
        } catch (UserAlreadyExistsException e) {
            throw new UserRegistrationException(e.getMessage());
        }
        return null;
    }
}