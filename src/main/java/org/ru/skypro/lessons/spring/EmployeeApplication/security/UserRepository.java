package org.ru.skypro.lessons.spring.EmployeeApplication.security;

import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    // Создаем метод findByUsername
    // для поиска пользователя по имени пользователя
    AuthUser findByUsername(String username);
}