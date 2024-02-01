package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController // Аннотация @Controller заменена на @RestController
@RequestMapping("/admin/api")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        // Метод getAllUsers() теперь возвращает ResponseEntity с объектом List<User>
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) {
        User user = userService.getById(id);

        if(user == null) {
            throw new NoSuchUserException("Нет пользователя с ID = " +
                    id + " в Базе Данных");
        }

        return user; //возвращается НЕ сам объект user, а JSON, соответствующий этому объекту
    }

    @PostMapping("/create")
    // Аннотация @ModelAttribute заменена на @RequestBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        // Метод addUser() теперь возвращает ResponseEntity с HttpStatus.CREATED
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // вариант с возвращением НЕ самого объекта user, а JSON, соответствующий обновленному пользователю
    @PutMapping("/edit")
    public User edit(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser (@PathVariable long id) {
        User user = userService.getById(id);

        if (user == null) {
            throw new NoSuchUserException("Пользователя с ID = " +
                    id + " не существует в Базе Данных");
        }
        userService.deleteById(id);

        return "Пользователь с ID = " + id + " был успешно удалён";
    }
}