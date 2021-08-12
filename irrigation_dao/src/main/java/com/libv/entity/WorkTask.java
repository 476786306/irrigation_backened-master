package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("work_task")
public class WorkTask {
    private long id;

    private long gardenerId;

    private String taskContent;

    private String pictureUrl;

    @TableField("is_done")
    private boolean whetherDone;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;

}
