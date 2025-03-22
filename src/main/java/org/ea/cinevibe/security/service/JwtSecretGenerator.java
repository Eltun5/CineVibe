package org.ea.cinevibe.security.service;

import java.util.Base64;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class JwtSecretGenerator {

    public String generateSecretKey() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
