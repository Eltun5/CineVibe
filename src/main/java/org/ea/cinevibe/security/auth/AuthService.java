package org.ea.cinevibe.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.dto.LoginRequestDTO;
import org.ea.cinevibe.dto.RegisterRequestDTO;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.model.enums.UserRole;
import org.ea.cinevibe.security.model.Token;
import org.ea.cinevibe.security.service.JwtService;
import org.ea.cinevibe.security.service.TokenService;
import org.ea.cinevibe.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> register(RegisterRequestDTO request){
        try{
            User user = User.builder().
                    username(request.username()).
                    password(passwordEncoder.encode(request.password())).
                    email(request.email()).
                    role(UserRole.USER).
                    build();

            userService.save(user);
            log.info("User successfully created!!!");
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created!!!");
        }catch (DataIntegrityViolationException e) {
            log.error("Username or Email exist!!!");
            return ResponseEntity.badRequest().body("Username or Email exist!!!");
        }
    }

    public ResponseEntity<String> logIn(LoginRequestDTO request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.Password());

            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (authenticate == null) {
                log.warn("Username or password is incorrect!!!");
                throw new RuntimeException("Username or password is incorrect!!!");
            }

            User user = userService.getUserByUsername(request.username());
            if (user == null) {
                log.warn("User not found!!! Username is " + request.username());
                throw new RuntimeException("User not found!!! Username is " + request.username());
            }

            String valueOfToken = jwtService.generateToken(request.username());

            Token token = new Token(valueOfToken,
                    false,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            tokenService.save(token);

            return ResponseEntity.ok(valueOfToken);
        } catch (Exception e) {
            log.error("Unexpected error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error during login: " + e.getMessage());
        }

    }

    public ResponseEntity<String> logOut(String authorizationHeader) {
        String valueOfToken = authorizationHeader.substring(7);

        Token token = tokenService.getTokenByValue(valueOfToken);

        if (token != null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenService.save(token);
            log.info("Successfully Logging out!!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully Logging out!!");
        }

        log.error("Error happened while signing out.");
        return ResponseEntity.badRequest().body("Error happened while signing out.");

    }
}
