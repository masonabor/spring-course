package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.exceptions.UserAlreadyExistsException;
import com.portfoliotracker.portfoliotracker.exceptions.UserRegistrationException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.repositories.InMemoryUserDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

    private InMemoryUserDAO inMemoryUserDAO;

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsUser(UserRegistrationDTO userDTO) throws UserAlreadyExistsException {
        boolean existsByUsername = inMemoryUserDAO.existsByUsername(userDTO.getUsername());
        boolean existsByEmail = inMemoryUserDAO.existsByEmail(userDTO.getEmail());
        if (existsByUsername) {
            throw new UserAlreadyExistsException("Акаунт з даним юзернеймом уже існує");
        } else if (existsByEmail) {
            throw new UserAlreadyExistsException("Акаунт з такою електронною поштою уже існує");
        } else if (existsByUsername && existsByEmail) {
            throw new UserAlreadyExistsException("Акаунти з таким юзернеймом та поштою уже існують");
        } else return false;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDTO userDTO) throws UserRegistrationException {
        try {
            boolean existsUser = existsUser(userDTO);
            if (!existsUser) {
                var user = new User(userDTO);
                inMemoryUserDAO.addUser(user);
                return user;
            }
        } catch (UserAlreadyExistsException e) {
            throw new UserRegistrationException(e.getMessage());
        }
        return null;
    }
}