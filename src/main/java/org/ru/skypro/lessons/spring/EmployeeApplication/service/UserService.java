package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;

public interface UserService {
    void addUser(AuthUser user);

    void deleteUserById(Long id);
}
