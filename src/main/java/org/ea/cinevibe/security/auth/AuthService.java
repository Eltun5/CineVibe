package org.ea.cinevibe.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.dto.LoginRequestDTO;
import org.ea.cinevibe.dto.RegisterRequestDTO;
import org.ea.cinevibe.excepions.NoDataFoundException;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.model.enums.UserRole;
import org.ea.cinevibe.security.model.Token;
import org.ea.cinevibe.security.model.VerificationCode;
import org.ea.cinevibe.security.service.EmailService;
import org.ea.cinevibe.security.service.JwtService;
import org.ea.cinevibe.security.service.TokenService;
import org.ea.cinevibe.security.service.VerificationCodeService;
import org.ea.cinevibe.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final VerificationCodeService verificationCodeService;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, TokenService tokenService, PasswordEncoder passwordEncoder, EmailService emailService, VerificationCodeService verificationCodeService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationCodeService = verificationCodeService;
    }

    public ResponseEntity<String> register(RegisterRequestDTO request) {
        try {
            User user = User.builder().
                    username(request.username()).
                    password(passwordEncoder.encode(request.password())).
                    email(request.email()).
                    role(UserRole.ROLE_USER).
                    build();

            userService.save(user);
            log.info("User successfully created.");
            return ResponseEntity.status(HttpStatus.CREATED).
                    body("User successfully created. " +
                         "Please verify email. Email:" + request.email());
        } catch (DataIntegrityViolationException e) {
            log.error("Username or Email exist!!!");
            return ResponseEntity.badRequest().body("Username or Email exist!!!");
        }
    }

    public ResponseEntity<String> logIn(LoginRequestDTO request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password());

            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (authenticate == null) {
                log.warn("Username or password is incorrect!!!");
                throw new BadCredentialsException("Username or password is incorrect!!!");
            }

            User user = userService.getUserByUsername(request.username());
            if (user == null) {
                log.warn("User not found!!! Username is " + request.username());
                throw new RuntimeException("User not found!!! Username is " + request.username());
            }

            if (!user.isEmailVerified()) {
                userService.delete(user);
                log.error("User didn't verified email!!!");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please do register completely!!!.");
            }

            String valueOfToken = jwtService.generateToken(request.username());

            Token token = new Token(valueOfToken,
                    false,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            tokenService.save(token);

            return ResponseEntity.ok(valueOfToken);
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password. ");

        } catch (LockedException e) {
            log.warn("User account is locked: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.LOCKED).
                    body("Your account is locked. Please contact support.");

        } catch (DisabledException e) {
            log.warn("User account is disabled: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body("Your account is disabled. Please contact support.");

        } catch (UsernameNotFoundException e) {
            log.warn("User not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("User not found. ");

        } catch (Exception e) {
            log.error("Unexpected error during sign-in: {}", e.getMessage());
            return ResponseEntity.internalServerError().
                    body("An unexpected error occurred. ");

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

    public ResponseEntity<String> sendVerificationCode(String email) {
        String code = emailService.sendVerificationCode(email);
        verificationCodeService.save(new VerificationCode(email, code, LocalDateTime.now().plusMinutes(5)));
        return ResponseEntity.ok(email);
    }

    public ResponseEntity<String> verifyCode(String email, String code) {
        try {
            VerificationCode verificationCode =
                    verificationCodeService.findByEmailAndCode(email, code);

            if (verificationCode.isExpired()) {
                throw new TimeoutException();
            }

            User user = userService.getUserByEmail(email);
            user.setEmailVerified(true);

            userService.save(user);

            log.info("Creation is successfully!");
            return ResponseEntity.ok("Creation is successfully!");
        } catch (TimeoutException e) {
            log.error("Time is over!!!");
            return ResponseEntity.status(HttpStatus.GONE).body("Time is over!!!");
        } catch (NoDataFoundException e) {
            log.info("Cannot find User!!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User cannot found!!!");
        } catch (Exception e) {
            log.error("Wrong Password!!!");
            return ResponseEntity.badRequest().body("Wrong Password!!!");
        }

    }
}
