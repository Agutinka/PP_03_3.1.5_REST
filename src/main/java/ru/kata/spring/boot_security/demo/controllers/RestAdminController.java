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
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController // Аннотация @Controller заменена на @RestController
@RequestMapping("/admin/api")
public class RestAdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);

        if(user == null) {
            throw new NoSuchUserException("Нет пользователя с ID = " +
                    id + " в Базе Данных");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    // Аннотация @ModelAttribute заменена на @RequestBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        // Метод addUser() теперь возвращает ResponseEntity с HttpStatus.CREATED
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> edit(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Пользователь с ID = " + id + " был успешно удалён", HttpStatus.ACCEPTED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List <Role> roleList = roleService.getAllRoles();
        return new ResponseEntity<>(roleList,HttpStatus.OK);
    }
}