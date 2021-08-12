package com.libv.emqx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttAfterServiceStarted implements ApplicationRunner {

    @Value("${interval-time}")
    private long intervalTime;

    @Autowired
    MqttPushClient mqttPushClient;

    @Override
    public void run(ApplicationArguments args) {
//         mqttPushClient.publishTimeToGateway("/sys/a1kG29TeXM7/device001/thing/event/property/post", intervalTime);
    }
}
