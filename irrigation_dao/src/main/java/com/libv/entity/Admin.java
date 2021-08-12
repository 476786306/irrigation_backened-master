package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("admin")
public class Admin {
    private long id;

    private String name;

    private String password;

    private String phone;

    private int sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String avatar;
}
