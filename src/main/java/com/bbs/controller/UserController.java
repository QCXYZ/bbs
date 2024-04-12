package com.bbs.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bbs.entity.User;
import com.bbs.exception.ResourceNotFoundException;
import com.bbs.repository.UserRepository;
import com.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRepository userRepository;

    private String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .sign(Algorithm.HMAC256("your_secret_key"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
        User registeredUser = userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("user_id", registeredUser.getId());
        response.put("message", "用户注册成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username,
                                       @RequestParam String password) throws Exception {
        User user = userService.validateUser(username, password);
        Map<String, Object> response = new HashMap<>();
        response.put("token", generateToken(user));
        response.put("user_id", user.getId());
        response.put("message", "登录成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{user_id}/profile")
    public ResponseEntity<Map<String, String>> updateUserProfile(@PathVariable Long user_id,
                                                                 @RequestBody User user) {
        User updatedUser = userService.updateUserProfile(user_id, user.getAvatar(), user.getNickname(), user.getBio());
        log.info("updatedUser: {}", updatedUser);
        Map<String, String> response = new HashMap<>();
        response.put("message", "用户资料更新成功");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{user_id}/password")
    public ResponseEntity<Map<String, String>> updatePassword(@PathVariable Long user_id,
                                                              @RequestParam String oldPassword,
                                                              @RequestParam String newPassword) {
        userService.updatePassword(user_id, oldPassword, newPassword);
        Map<String, String> response = new HashMap<>();
        response.put("message", "密码更新成功");
        return ResponseEntity.ok(response);
    }
}
