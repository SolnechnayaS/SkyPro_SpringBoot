package org.ru.skypro.lessons.spring.EmployeeApplication;

import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);

	}

}
