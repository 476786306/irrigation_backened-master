package com.libv.emqx;

import cn.hutool.json.JSONUtil;
import com.libv.mqtt.entity.Instruction;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 存在服务端主动踢出客户端后
 * 客户端重连无法收到订阅消息（第一次上线）
 * 如果此时客户端再次断开连接重连（第二次上线）
 * 会发生重复两次消费在被踢出到第二次上线时间内收到的消息
 */
@Component
@Slf4j
public class MqttPushClient {
    @Value("${interval-time}")
    private long intervalTime;

    /**
     * MQTT服务端ip及端口
     */

    private static String host = "tcp://121.4.216.6:1883";

    /**
     * 账号
     */
    private static String username = "server";

    /**
     * 密码
     */
    private static String password = "server";

    /**
     * clientID
     */
    private static String clientId = "server-backend";

    /*
    * SUBSCRIPTION_THEME
    * */
    private static String SUBSCRIPTION_THEME =
            "/sys/a1kG29TeXM7/device001/thing/event/property/post";

    /**
     * MQTT-Client
     */
    private static MqttClient client;

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");


    /**
     * 描述：订阅信息
     */
    public void subscribe() {
        try {
            this.getClient().subscribe(SUBSCRIPTION_THEME);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /**
     * 描述：获取MqttClient
     */
    public static MqttClient getClient() {
        try {
            if (client == null) {
                client = new MqttClient(host, clientId);
                MqttConnectOptions conOptions = new MqttConnectOptions();
                conOptions.setUserName(username);
                conOptions.setPassword(password.toCharArray());
                conOptions.setCleanSession(false);
                conOptions.setAutomaticReconnect(true);
                conOptions.setConnectionTimeout(10);
                conOptions.setKeepAliveInterval(20);
//                conOptions.setWill(SUBSCRIPTION_THEME, "close".getBytes(), 1, true);
                client.connect(conOptions);
                client.setCallback(new MqttCallBack());
            }
            if (!client.isConnected()) {
                client.reconnect();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return client;
    }

    /**
     * 向网关定时发送当前时间
     */
    public void publishTimeToGateway(String pubTopic, long interval) {
        MqttTopic topic = this.getClient().getTopic(pubTopic);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String currentTime = formatter.print(LocalDateTime.now());
                    MqttMessage message = new MqttMessage(currentTime.getBytes());
                    message.setQos(0);
                    topic.publish(message);
                } catch (MqttException e) {
                    log.warn(e.getMessage());
                }
            }
        }, 0, interval);
    }

    public void publishMessageToGateway(Instruction instruction) {
        MqttTopic topic = this.getClient().getTopic(SUBSCRIPTION_THEME);
        String currentTime = formatter.print(LocalDateTime.now());
        MqttMessage message = new MqttMessage(currentTime.getBytes());
        message.setQos(0);
        message.setRetained(true);
        String strMessage = JSONUtil.toJsonStr(instruction);
        message.setPayload(strMessage.getBytes());
        System.out.println(strMessage);
        try {
            if (!client.isConnected()) {
                client.reconnect();
            }
            topic.publish(message);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}