package com.libv.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SnowflakeUtil {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long workerId = 0;//为终端ID
    private final long datacenterId = 1;//数据中心ID
    private final Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
    }

    public synchronized long getSnowflakeId() {
        return snowflake.nextId();
    }

    public synchronized long getSnowflakeId(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
}
