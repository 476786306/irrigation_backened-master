package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@TableName("expert")
public class Expert {
    private long id;

    private String name;

    private String password;

    private long adminId;

    private String phone;

    private int sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String major;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;
}
