package org.ru.skypro.lessons.spring.EmployeeApplication.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AuthUser {

    // Создаем поле id для хранения идентификатора пользователя.
    @Id
    // Используем AUTO-генерацию идентификаторов.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;

    // Создаем поле username для хранения имени пользователя.
    // Устанавливаем ограничение на уникальность значения в колонке
    // и запрет на NULL.
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // Создаем поле password для хранения пароля пользователя
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    int enabled;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authority> authorityList;

    // standard getters and setters


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUser authUser)) return false;
        return Objects.equals(getUserId(), authUser.getUserId()) && getUsername().equals(authUser.getUsername()) && getPassword().equals(authUser.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword());
    }
}