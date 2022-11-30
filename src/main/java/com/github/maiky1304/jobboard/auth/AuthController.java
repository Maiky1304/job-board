package com.github.maiky1304.jobboard.auth;

import com.github.maiky1304.jobboard.security.jwt.model.AuthRequest;
import com.github.maiky1304.jobboard.security.jwt.model.AuthResponse;
import com.github.maiky1304.jobboard.session.SessionService;
import com.github.maiky1304.jobboard.user.User;
import com.github.maiky1304.jobboard.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> onLogin(@RequestBody AuthRequest request) {
        AuthResponse response = userService.handleAuthentication(authenticationManager, request);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public User onRegister(@RequestBody User user) {
        return userService.handleRegistration(passwordEncoder, user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> onLogout(Authentication authentication) {
        User user = userService.extractFromAuthentication(authentication);
        return ResponseEntity.ok(sessionService.invalidateSessionByUser(user));
    }

}
