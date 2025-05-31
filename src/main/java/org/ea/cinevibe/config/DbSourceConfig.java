package org.ea.cinevibe.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "org.ea.cinevibe.repository",
                "org.ea.cinevibe.security.repository"
        }
)
public class DbSourceConfig {
    @Value("${spring.datasource.url}")
    private String DbUrl;

    @Value("${spring.datasource.username}")
    private String DbUsername;

    @Value("${spring.datasource.password}")
    private String DbPassword;

    @Bean(name = "dataSource")
    public DataSource secondDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(DbUrl)
                .username(DbUsername)
                .password(DbPassword)
                .build();

        dataSource.setPoolName("ProductDbHikariPool");
        return dataSource;
    }
}
