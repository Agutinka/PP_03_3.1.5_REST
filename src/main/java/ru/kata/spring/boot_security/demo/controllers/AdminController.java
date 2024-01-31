package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController // Аннотация @Controller заменена на @RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        // Метод getAllUsers() теперь возвращает ResponseEntity с объектом List<User>
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<User> create() {
        User user = new User();
        // Метод create() теперь возвращает ResponseEntity с объектом User
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    // Аннотация @ModelAttribute заменена на @RequestBody
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        userService.save(user);
        // Метод addUser() теперь возвращает ResponseEntity с HttpStatus.CREATED
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/edit")
    public ResponseEntity<User> edit(@RequestParam(value = "id", required = false) Long id) {
        User user = userService.getById(id);
        // Метод edit() теперь возвращает ResponseEntity с объектом User
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/edit")
    // Аннотация @ModelAttribute заменена на @RequestBody
    public ResponseEntity<Void> update(@RequestBody User userForm) {
        userService.update(userForm);
        // Метод update() теперь возвращает ResponseEntity с HttpStatus.NO_CONTENT (возвращает статус 204 (No Content) для обозначения того, что обновление было успешным)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // вариант с возвращением обновленного пользователя вместе с статусом 200 (OK) для обозначения того, что обновление было успешным и мы возвращаем обновленного пользователя
//    @PostMapping("/edit")
//public ResponseEntity<User> update(@RequestBody User userForm) {
//    userService.update(userForm);
//    return new ResponseEntity<>(userForm, HttpStatus.OK);
//

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam(value = "id") Long id) {
        userService.deleteById(id);
        // Метод delete() теперь возвращает ResponseEntity с HttpStatus.NO_CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}