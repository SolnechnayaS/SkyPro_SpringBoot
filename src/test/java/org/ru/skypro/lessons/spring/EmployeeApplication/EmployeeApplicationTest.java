package org.ru.skypro.lessons.spring.EmployeeApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class EmployeeApplicationTest {

        @Container
        private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withUsername("postgres")
                .withPassword("postgres");

        @DynamicPropertySource
        static void postgresProperties(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", postgres::getJdbcUrl);
                registry.add("spring.datasource.username", postgres::getUsername);
                registry.add("spring.datasource.password", postgres::getPassword);
        }

        @Test
        void contextLoads() {
        }

}