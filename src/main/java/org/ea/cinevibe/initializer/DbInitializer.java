package org.ea.cinevibe.initializer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Component
public class DbInitializer {

    private final DataSource dataSource;

    public DbInitializer(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/init.sql"));
            log.info("DB schema.Sql applied successfully.");
        } catch (Exception e) {
            log.error("Failed to apply schema.Sql to DB: " + e.getMessage());
        }
    }
}
