package org.ru.skypro.lessons.spring.EmployeeApplication.exception;

public class IncorrectEmployeeIdException extends Exception {
    public IncorrectEmployeeIdException(String message) {
        super(message);
    }
}
