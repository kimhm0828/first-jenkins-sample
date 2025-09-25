package dev.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User("Alice"));
        users.add(new User("Bob"));
        users.add(new User("Charlie"));
    }

    @GetMapping
    public List<User> getAll() {
        log.info("GET /api/users 호출됨, 총 사용자 수: ", users.size());
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        users.add(user);
        log.info("New user created: {}", user.getName());
        return user;
    }

    public static class User {
        private String name;

        public User() { }

        public User(String name) { this.name = name; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
