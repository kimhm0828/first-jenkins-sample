package dev.example.controller;

import dev.example.model.User;
import dev.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // SonarQube: final 필드는 생성자에서 주입해야 함 (Injection 방식 권고)
    // 현재는 필드 인젝션으로 되어있다고 가정
    // @Autowired
    // private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * [문제 1 연동] 검색어에 SQL Injection 취약점이 발생할 수 있습니다.
     * @param name 검색어
     */
    @GetMapping("/search")
    public List<User> search(@RequestParam String name) {
        return userService.searchUsers(name);
    }

    /**
     * [문제 3 연동] 복잡한 승격 로직을 호출합니다.
     */
    @PostMapping("/promote")
    public String promote(@RequestBody User user,
                          @RequestParam int score,
                          @RequestParam boolean isManager) {
        // [문제 6: 불필요한 null 검사]
        // Spring @RequestBody는 null일 가능성이 낮지만, 다른 곳에서 전달될 경우를 대비해
        // 여기서 user == null 체크가 없으면 NullPointerDereference 버그가 될 수 있습니다.
        // 현재는 Controller 수준에서 간단한 로직이므로 큰 문제로 보이지 않으나,
        // Service에서 처리하는 것이 일반적입니다.

        return userService.processUserPromotion(user, score, isManager);
    }
}
