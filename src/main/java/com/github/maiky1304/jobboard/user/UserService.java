package com.github.maiky1304.jobboard.user;

import com.github.maiky1304.jobboard.security.jwt.JwtTokenUtil;
import com.github.maiky1304.jobboard.security.jwt.model.AuthRequest;
import com.github.maiky1304.jobboard.security.jwt.model.AuthResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    /**
     * Loads a user by looking for it in the {@see Authentication} object.
     * @param authentication The authentication object.
     * @return The user or null if not found.
     */
    public @Nullable User extractFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal() == null) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }

    public @Nullable AuthResponse handleAuthentication(AuthenticationManager authenticationManager, AuthRequest request) {
        String email = request.getEmail(), password = request.getPassword();

        boolean authenticated = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password))
                .isAuthenticated();
        if (!authenticated) {
            return null;
        }

        User user = userRepository.findByEmail(request.getEmail());
        String token = jwtTokenUtil.generateJwt(user);

        return new AuthResponse(token);
    }

    public User handleRegistration(PasswordEncoder passwordEncoder, User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

}
