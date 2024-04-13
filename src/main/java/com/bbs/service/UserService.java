package com.bbs.service;

import com.bbs.entity.User;
import com.bbs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    public User register(String username, String password, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new RuntimeException("用户或邮箱已存在。");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        return userRepository.save(newUser);
    }

    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在!");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误!");
        }
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("用户不存在!"));
    }

    public User updateUserProfile(Long userId, String avatar, String nickname, String bio) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("用户不存在!"));
        user.setAvatar(avatar);
        user.setNickname(nickname);
        user.setBio(bio);
        return userRepository.save(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("用户不存在!"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码不匹配。");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
