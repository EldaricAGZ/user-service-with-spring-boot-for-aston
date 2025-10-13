package com.example.exception.handler;

import com.example.exception.exception.ListUsersIsEmptyException;
import com.example.exception.exception.NotValidFieldException;
import com.example.exception.exception.OtherException;
import com.example.exception.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UserNotFoundException userNotFoundException) {
        logger.error(userNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundException.getMessage());
    }

    @ExceptionHandler(ListUsersIsEmptyException.class)
    public ResponseEntity<String> handleListOfUsersIsEmpty(ListUsersIsEmptyException listUsersIsEmptyException) {
        logger.warn(listUsersIsEmptyException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listUsersIsEmptyException.getMessage());
    }

    @ExceptionHandler(NotValidFieldException.class)
    public ResponseEntity<String> handleNotValidException(NotValidFieldException notValidFieldException) {
        logger.error(notValidFieldException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValidFieldException.getMessage());
    }

    @ExceptionHandler(OtherException.class)
    public ResponseEntity<String> handleOtherException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
