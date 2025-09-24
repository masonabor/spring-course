package com.portfoliotracker.portfoliotracker.models;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder.Default
    private String firstName = null;

    @Builder.Default
    private String lastName = null;

    @Builder.Default
    private String phoneNumber = null;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_USER));

    @Builder.Default
    private boolean enabled = false;

    public static User of(String username, String password, String email) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

    public static User of(UserRegistrationDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public enum Role implements GrantedAuthority {
        ROLE_USER,
        ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }
}
