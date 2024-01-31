package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RestController // Аннотация @Controller заменена на @RestController
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    // Параметр Model был удален, так как в REST контроллерах не используется модель для передачи данных в представление
    public ResponseEntity<User> show(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        // Метод show() теперь возвращает ResponseEntity с объектом User
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}