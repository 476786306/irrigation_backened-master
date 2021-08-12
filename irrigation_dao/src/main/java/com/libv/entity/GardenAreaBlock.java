package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author longjie
 * @since 2021/4/25 18:29
 */
@Data
@TableName("garden_block")
public class GardenAreaBlock {
    private Long id;

    private Long gardenId;

    private String blockName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private int blockStatus;
}
