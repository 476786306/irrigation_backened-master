package com.libv.user;

import com.libv.entity.User;
import com.libv.util.R;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    R login(String phone,String password);

    R registry(User user);

    R getInfo(HttpServletRequest request);
}
