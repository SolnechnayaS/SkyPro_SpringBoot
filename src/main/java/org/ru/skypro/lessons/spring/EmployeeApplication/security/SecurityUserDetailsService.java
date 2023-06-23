//package org.ru.skypro.lessons.spring.EmployeeApplication.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//// Создаем класс SecurityUserDetailsService, который реализует интерфейс UserDetailsService
//public class SecurityUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    // Внедряем зависимость UserRepository для доступа к базе пользователей
//    private UserRepository userRepository;
//
//    @Override
//    // Реализация метода loadUserByUsername,
//    // который принимает строку с именем пользователя.
//    public UserDetails loadUserByUsername(String username) {
//        // Ищем пользователя в базе данных с указанным именем пользователя
//        AuthUser user = userRepository.findByUsername(username);
//        // Если пользователь не найден, выбрасываем исключение
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        // Возвращаем найденного пользователя, завёрнутого в объект
//        // класса SecurityUserPrincipal. Он отвечает за предоставление
//        // пользовательской информации при аутентификации.
//        return new SecurityUserPrincipal(user);
//    }
//}