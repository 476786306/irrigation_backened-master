package com.libv.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("garden_manager")
public class GardenManager implements Serializable {
    private long id;

    private String name;

    private String password;

    private String phone;

    private int sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private long adminId;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String avatar;

    private int loginStatus;

    private int identity;
}
