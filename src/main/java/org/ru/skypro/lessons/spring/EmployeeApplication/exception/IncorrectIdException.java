package org.ru.skypro.lessons.spring.EmployeeApplication.exception;

public class IncorrectIdException extends NullPointerException {
    public IncorrectIdException(String message) {
        super(message);
    }
}
