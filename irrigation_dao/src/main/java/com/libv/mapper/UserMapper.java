package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.dto.UserDTO;
import com.libv.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    UserDTO getInfo(@Param("userId") long userId);
}
