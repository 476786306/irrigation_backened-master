package com.libv.emqx;

import cn.hutool.json.JSONUtil;
import com.libv.controller.garden.DeviceDataController;
import com.libv.emqx.MqttPushClient;
import com.libv.entity.DeviceData;
import com.libv.garden.DeviceDataService;
import com.libv.garden.impl.DeviceDataImpl;
import com.libv.mqtt.entity.GateWayData;
import com.libv.util.SpringUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MqttCallBack implements MqttCallback {

    ApplicationContext context = SpringUtil.context;  //获取Spring容器
    DeviceDataService deviceDataService = context.getBean(DeviceDataService.class);  //获取bean
    DeviceDataController deviceDataController = new DeviceDataController() ;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开，可以做重连");
        MqttPushClient.getClient();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
        GateWayData gateWayData = JSONUtil.toBean(new String(message.getPayload()),GateWayData.class);
        if(gateWayData != null){
            DeviceData deviceData = new DeviceData();
            deviceData.setDeviceid(gateWayData.getId());
            deviceData.setChannel(gateWayData.getChannel());
            deviceData.setAddress(gateWayData.getAddress());
            deviceData.setGatetime(gateWayData.getTime());
            deviceData.setHumidity(gateWayData.getHumidity());
            deviceData.setTemperature(gateWayData.getTemperture());
            deviceData.setNitrogen(gateWayData.getNitrogen());
            deviceData.setPhosphorus(gateWayData.getPhosphorus());
            deviceData.setPotassium(gateWayData.getPotassium());
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            deviceData.setSystemtime(df.format(day));
            deviceDataService.addSubDeviceData(deviceData);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
