package com.libv.user.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.dto.UserDTO;
import com.libv.entity.User;
import com.libv.mapper.UserMapper;
import com.libv.user.UserService;
import com.libv.util.JwtUtil;
import com.libv.util.R;
import com.libv.util.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Override
    public R login(String phone, String password) {
        if (StrUtil.isEmpty(password) || !Validator.isMobile(phone)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        User one = baseMapper.selectOne(queryWrapper);
        if (null == one) {
            return R.error().message("用户不存在或密码错误");
        }
        String jwtToken = JwtUtil.getJwtToken(one.getId());
        return R.ok().data("token", jwtToken);
    }

    @Override
    public R registry(User user) {
        if (StrUtil.isEmpty(user.getName()) || !Validator.isMobile(user.getPhone())
                || StrUtil.isEmpty(user.getPassword())) {
            return R.error().message("参数非法");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count != 0) {
            return R.error().message("此手机号已注册");
        }
        user.setPassword(DigestUtil.sha1Hex(user.getPassword()));
        user.setId(snowflakeUtil.getSnowflakeId());
        try {
            baseMapper.insert(user);
        } catch (Exception e) {
            return R.error().message("注册失败！请稍后再试");
        }
        return R.ok().message("注册成功");
    }

    @Override
    public R getInfo(HttpServletRequest request) {
        Long userId = JwtUtil.getIdFromRequest(request);
        if (null == userId) {
            return R.error().message("缺少必要参数");
        }
        UserDTO userDTO = baseMapper.getInfo(userId);
        if (null == userDTO) {
            return R.error().message("获取信息为空");
        }
        return R.ok().data("info", userDTO);
    }
}
