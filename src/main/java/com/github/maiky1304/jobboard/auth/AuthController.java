package com.github.maiky1304.jobboard.auth;

import com.github.maiky1304.jobboard.security.jwt.model.AuthRequest;
import com.github.maiky1304.jobboard.security.jwt.model.AuthResponse;
import com.github.maiky1304.jobboard.user.User;
import com.github.maiky1304.jobboard.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
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

}
