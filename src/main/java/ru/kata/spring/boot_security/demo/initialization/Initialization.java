package ru.kata.spring.boot_security.demo.initialization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Initialization {
    UserService userService;
    RoleService roleService;

    @Autowired
    public Initialization(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Transactional
    @PostConstruct
    public void run() {

        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(roleService.showUserById(2L));
        adminRole.add(roleService.showUserById(1L));
        userRole.add(roleService.showUserById(2L));

        userService.save(new User(1L, "admin", "admin", 35, "admin@mail.ru", "admin", adminRole));
        userService.save(new User(2L, "test_user1", "user1", 11, "user1@mail.ru", "test_password", userRole));
    }
}
