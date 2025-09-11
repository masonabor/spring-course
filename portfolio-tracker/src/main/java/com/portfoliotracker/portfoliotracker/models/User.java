package com.portfoliotracker.portfoliotracker.models;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Set<Role> roles = new HashSet<>();
    private boolean activated;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.roles.add(Role.ROLE_USER);
        this.activated = false;
    }

    public User(UserRegistrationDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.firstName = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.roles.add(Role.ROLE_USER);
        this.activated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public enum Role implements GrantedAuthority {
        ROLE_USER,
        ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
