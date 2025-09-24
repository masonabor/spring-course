package com.portfoliotracker.portfoliotracker.services;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.exceptions.UserAlreadyExistsException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.models.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    User registerNewUserAccount(UserRegistrationDTO dto);
//    Optional<User> findById(Long id);
//    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);
//    List<User> findAll();
//    void deleteById(Long id);
////    User updateProfile(Long userId, UserProfileUpdateDto dto);
//    void changePassword(Long userId, String newPassword);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsUser(UserRegistrationDTO userDTO) throws UserAlreadyExistsException;
    void saveRegisteredUser(User user);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String token);
//    void activateUser(Long userId);
//    void deactivateUser(Long userId);
//    void setUserEnabled(Long userId, boolean enabled);
//    List<User> findAllByEnabled(boolean enabled);
//    void confirmEmail(Long userId);
//    void resendVerificationEmail(Long userId);
//    User getCurrentAuthenticatedUser();
//    void assignRole(Long userId, String role);
//    String getUserRole(Long userId);
}
