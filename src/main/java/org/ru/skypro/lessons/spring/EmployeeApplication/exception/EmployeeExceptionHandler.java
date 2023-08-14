package org.ru.skypro.lessons.spring.EmployeeApplication.exception;

//import org.ru.skypro.lessons.spring.EmployeeApplication.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

    // Метод обработки исключений IOException.
    // Срабатывает, когда в контроллере возникает IOException.
    @ExceptionHandler
    public ResponseEntity<?> handlerIOException(IOException ioException) {
        // Возвращает статус 404 (Not Found) при возникновении IOException.
        logger.error("Not Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Метод обработки исключений SQLException.
    // Срабатывает, когда в контроллере возникает SQLException.
    @ExceptionHandler
    public ResponseEntity<?> handlerSQLException(SQLException sqlException) {
        // Возвращает статус 500 (Internal Server Error)
        // при возникновении SQLException.
        logger.error("Internal Server Error");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Метод обработки исключений Exception.
    // Срабатывает, когда в контроллере возникает Exception.

    @ExceptionHandler
    public ResponseEntity<?> handlerException(Exception exception) {
        // Возвращает статус 403 (Forbidden) при возникновении Exception.
        logger.error("Forbidden");
        return new ResponseEntity<>("Ошибка запроса, не переданы параметры",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNullPointerException(NullPointerException nullPointerException) {
        logger.error("Null Pointer Exception");
        return new ResponseEntity<>("Данные не найдены",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleError(UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<?> handleError(IncorrectIdException e) {
        return ResponseEntity.notFound().build();
    }

}
