package ru.kata.spring.boot_security.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler //аннотация для метода, ответственного за обработку исключений
    public ResponseEntity<UserIncorrectData> hadleException(
            NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler //аннотация для метода, ответственного за обработку исключений
    public ResponseEntity<UserIncorrectData> hadleException(
            Exception exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
