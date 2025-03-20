package org.ea.cinevibe.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.security.model.Token;
import org.ea.cinevibe.security.service.JwtService;
import org.ea.cinevibe.security.service.TokenService;
import org.ea.cinevibe.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class CustomJwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    private final TokenService tokenService;

    public CustomJwtFilter(JwtService jwtService, UserService userService, TokenService tokenService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenValue = authHeader.substring(7);

        String username = jwtService.extractUsername(tokenValue);
        try {
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var customUserDetails = userService.getUserByUsername(username).getCustomUserDetails();
                Token token = tokenService.getTokenByValue(tokenValue);
                boolean isValidToken = token.isExpired() && token.isRevoked();

                if (isValidToken && jwtService.isValidToken(tokenValue)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            customUserDetails,
                            null,
                            customUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
