package com;

import com.libv.emqx.MqttPushClient;
import com.libv.garden.DeviceService;
import com.libv.garden.impl.DeviceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * java目录下的xml文件默认不会放到classpath目录下
 * 所以将配置文件需要放到resources目录下面
 */
@SpringBootApplication
@MapperScan("com.libv.mapper")
@EnableScheduling
public class IrrigationMain {

    @Autowired

    public static void main(String[] args) {
        MqttPushClient mqttPushClient = new MqttPushClient();
        SpringApplication.run(IrrigationMain.class, args);
        mqttPushClient.subscribe();
    }

}
