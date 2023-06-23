package org.ru.skypro.lessons.spring.EmployeeApplication.config;
import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthorityTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
// Включаем поддержку Web Security.
@Configuration
// Объявляем класс SecurityConfig как класс конфигурации.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/*"))
                .authorizeHttpRequests(this::customizeRequest)
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());
        return http.build();
    }

    private void customizeRequest(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry registry) {
        try {
            registry
                    .requestMatchers(new AntPathRequestMatcher("/admin/**"))
                    .hasAnyRole(AuthorityTypes.ADMIN.getType())
                    // Определяем правило авторизации для запросов
                    // к URL, которые начинаются с "/admin/",
                    // позволяя доступ только пользователям с ролью "ADMIN".

                    .requestMatchers(new AntPathRequestMatcher("/**"))
                    .hasAnyRole(AuthorityTypes.USER.getType());
            // Определяем правило авторизации для остальных запросов,
            // позволяя доступ только пользователям с ролью "USER".

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//     Создаем бин userDetailsManager.
//     Он использует JdbcUserDetailsManager для работы с базой данных.
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource,
//                                                 AuthenticationManager authenticationManager) {
//
//        // Инициализируем JdbcUserDetailsManager с dataSource
//        // и authenticationManager для работы с базой данных
//        JdbcUserDetailsManager jdbcUserDetailsManager =
//                new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
//        return jdbcUserDetailsManager;
//    }

    @Bean
    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {

        // Создаем пользователя Ivan с ролью USER
        UserDetails ivan = User.withUsername("Ivan")
                .password(passwordEncoder.encode("ivan1234"))
                .roles("USER")
                .build();

        // Создаем пользователя Vladimir с ролью USER
        UserDetails vladimir = User.withUsername("Vladimir")
                .password(passwordEncoder.encode("vladimir1234"))
                .roles("USER")
                .build();

        // Создаем пользователя admin с ролью ADMIN
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin1234"))
                .roles("USER","ADMIN")
                .build();

        // Возвращаем новый сервис управления InMemoryUserDetailsManager
        // с добавленными пользователями (Ivan, Vladimir, admin)
        return new InMemoryUserDetailsManager(ivan, vladimir, admin);
    }

    // Создаем бин authenticationManager и получаем его
    // из AuthenticationConfiguration.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    // Внедряем зависимость UserDetailsService
    // для работы с данными пользователя.
    private UserDetailsService userDetailsService;

    @Bean
    // Создаем экземпляр PasswordEncoder для шифрования паролей.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Создаем экземпляр DaoAuthenticationProvider
    // для работы с аутентификацией через базу данных.
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // Устанавливаем наш созданный экземпляр PasswordEncoder
        // для возможности использовать его при аутентификации.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
