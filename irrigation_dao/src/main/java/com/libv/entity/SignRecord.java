package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

@Data
@TableName("sign_record")
public class SignRecord {
    private int id;

    private long userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Timestamp signTime;

    private String signArea;
}
