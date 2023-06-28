package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;
import org.ru.skypro.lessons.spring.EmployeeApplication.security.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(AuthUser user) {
        String userName = user.getUsername();
        if (userRepository.findByUsername(userName) == null) {
            logger.info("add User="+userName);
            userRepository.save(user);
        } else {
            new ResponseEntity<>("Пользователь username=" + userName +
                    " уже есть в базе данных", HttpStatus.BAD_REQUEST);
            logger.error("User="+userName+" exists");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            AuthUser user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
            logger.info("delete User By Id="+id);
        }
        catch (NullPointerException e)
        {

            new ResponseEntity<>("Пользователь id=" + id +
                    " отсутствует в базе данных", HttpStatus.BAD_REQUEST);
            logger.error("User id=" + id +
                    "missing in the database");
        }
    }


}
