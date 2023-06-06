package org.ru.skypro.lessons.spring.EmployeeApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    // Метод обработки исключений IOException.
    // Срабатывает, когда в контроллере возникает IOException.
    @ExceptionHandler
    public ResponseEntity<?> handlerIOException(IOException ioException) {
        // Возвращает статус 404 (Not Found) при возникновении IOException.
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Метод обработки исключений SQLException.
    // Срабатывает, когда в контроллере возникает SQLException.
    @ExceptionHandler
    public ResponseEntity<?> handlerSQLException(SQLException sqlException) {
        // Возвращает статус 500 (Internal Server Error)
        // при возникновении SQLException.
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Метод обработки исключений Exception.
    // Срабатывает, когда в контроллере возникает Exception.
    @ExceptionHandler
    public ResponseEntity<?> handlerException(Exception exception) {
        // Возвращает статус 403 (Forbidden) при возникновении Exception.
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNullPointerException(NullPointerException nullPointerException) {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
