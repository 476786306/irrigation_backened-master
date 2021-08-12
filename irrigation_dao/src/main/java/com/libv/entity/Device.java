package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author longjie
 * @since 2021/4/26 14:05
 */
@Data
@TableName("device")
public class Device {
    private Long id;

    private String deviceName;

    private String deviceStatus;

    private int deviceType;

    private Long blockId;

    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
