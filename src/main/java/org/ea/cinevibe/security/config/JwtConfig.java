package org.ea.cinevibe.security.config;

import jakarta.annotation.PostConstruct;
import org.ea.cinevibe.security.service.JwtSecretGenerator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtConfig {

    private final JwtSecretGenerator jwtSecretGenerator;

    private final Environment environment;

    public JwtConfig(JwtSecretGenerator jwtSecretGenerator, Environment environment) {
        this.jwtSecretGenerator = jwtSecretGenerator;
        this.environment = environment;
    }

    @PostConstruct
    public void initializeSecretKey() {
        if (Objects.requireNonNull(environment.getProperty("jwt.secretKey")).isEmpty()) {
            String jwtSecret = jwtSecretGenerator.generateSecretKey();
            updateApplicationYaml(jwtSecret);
        }
    }

    private void updateApplicationYaml(String secret) {
        try (FileWriter writer = new FileWriter("src/main/resources/application.yaml", true)) {
            writer.write(" " + secret + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
