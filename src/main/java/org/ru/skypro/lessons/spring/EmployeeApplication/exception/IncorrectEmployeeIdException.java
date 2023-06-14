package org.ru.skypro.lessons.spring.EmployeeApplication.exception;

public class IncorrectEmployeeIdException extends NullPointerException {
    public IncorrectEmployeeIdException(String message) {
        super(message);
    }
}
