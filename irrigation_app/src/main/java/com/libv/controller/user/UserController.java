package com.libv.controller.user;

import com.libv.entity.User;
import com.libv.user.UserService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 业主接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R login(String phone, String password) {
        return userService.login(phone, password);
    }

    @PostMapping("/registry")
    public R registry(@RequestBody User user) {
        return userService. registry(user);
    }

    @GetMapping("/info")
    public R getInfo(HttpServletRequest request) {
        return userService.getInfo(request);
    }
}

