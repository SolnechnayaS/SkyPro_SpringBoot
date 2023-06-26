package org.ru.skypro.lessons.spring.EmployeeApplication.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AuthUser {

    private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    int enabled;

    @ManyToOne
    @JoinColumn (name = "authority")
    protected Authority authority;

}