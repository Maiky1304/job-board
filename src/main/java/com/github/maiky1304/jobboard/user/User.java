package com.github.maiky1304.jobboard.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Field 'firstName' is mandatory.")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Field 'lastName' is mandatory.")
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Field 'email' is mandatory.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Field 'password' is mandatory.")
    private String password;

    private Boolean locked = false;
    private Boolean enabled = true;

    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Long id, String email, String password) {
        this(email, password);
        this.id = id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role.toGrantedAuthority());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
