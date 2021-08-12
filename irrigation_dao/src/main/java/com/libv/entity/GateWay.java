package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gateway")
public class GateWay {
    private long id;
    private long gardenAreaId;
}
