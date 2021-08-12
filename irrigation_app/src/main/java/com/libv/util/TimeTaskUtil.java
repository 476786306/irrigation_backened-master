package com.libv.util;

import com.libv.emqx.MqttPushClient;
import com.libv.entity.Device;
import com.libv.entity.DeviceData;
import com.libv.entity.DeviceReservation;
import com.libv.garden.DeviceDataService;
import com.libv.garden.DeviceService;
import com.libv.garden.DeviceSheduleService;
import com.libv.mqtt.entity.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TimeTaskUtil {

    @Autowired
    DeviceSheduleService sheduleService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceDataService deviceDataService;

    @Autowired
    MqttPushClient mqttPushClient;

    List<DeviceReservation>deviceReservationList = new ArrayList<>();

    /**
     * 首次延时1s，然后每2秒执行一次
     */
    boolean isSendMsgToGateWay = false;

    @Scheduled(initialDelay = 1000,fixedRate = 5000)
    public void getAllDeviceReservation()
    {

        deviceReservationList = sheduleService.selectSwitchControlShedule();
        System.out.println(deviceReservationList.toArray());

    }

    @Scheduled(initialDelay = 1000,fixedRate = 2000)
    public void sendMessageToGateWay()
    {
        accordReservationSendMsgToGateWay();

        accordHumiditySendMsgToGateWay();

    }


    void  accordReservationSendMsgToGateWay(){
        if(deviceReservationList != null) {
//            System.out.println("开始轮询预约开关"+deviceReservationList.size()+"个");
            for(int i = 0;i < deviceReservationList.size();i++){
                Date dt = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String currenttimestamp = df.format(dt);
                DeviceReservation deviceReservation = deviceReservationList.get(i);
                String reservationtime = df.format(deviceReservation.getResverTime());
                if (currenttimestamp.equals(reservationtime)){
                    Instruction instruction = new Instruction();
                    instruction.setAddress(deviceReservation.getAddress());
                    instruction.setChannel(23);
                    instruction.setPowerSwitch(1);
                    mqttPushClient.publishMessageToGateway(instruction);
                    sheduleService.setSwitchControlShedule(1,deviceReservation.getId());
                    deviceService.setSwitchStatusByDeviceId(deviceReservation.getDeviceId(),1);
//                    System.out.println("发送网关打开开关成功");
                }
                String stoptime = df.format(deviceReservation.getStopTime());
                if(currenttimestamp.equals(stoptime)){
                    Instruction instruction = new Instruction();
                    instruction.setAddress(deviceReservation.getAddress());
                    instruction.setChannel(23);
                    instruction.setPowerSwitch(0);
                    mqttPushClient.publishMessageToGateway(instruction);
                    sheduleService.setSwitchControlShedule(2,deviceReservation.getId());
                    deviceService.setSwitchStatusByDeviceId(deviceReservation.getDeviceId(),0);
//                    System.out.println("发送网关关闭开关成功");
                }

            }
        }
    }

    void accordHumiditySendMsgToGateWay(){  //根据湿度值发消息给网关
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String currenttimestamp = df.format(dt);
        String startTime = "22:00:00";
        String endTime = "23:30:00";
        if(DataUtil.isEffectiveTime(currenttimestamp,startTime,endTime)){  //在17：00范围内开始自动检测湿度进行喷水
            DeviceData deviceData = deviceDataService.selectLatestDeviceData();
            String testblockid = "1368125197445625862";
//            System.out.println(Long.valueOf(testblockid));
            if(deviceData != null && !StringUtils.isEmpty(deviceData.getHumidity())){
                float humidity = (float)DataUtil.hexToDecimal(deviceData.getHumidity()) / 10;
                System.out.println("现在西山园区的湿度值是"+humidity);
                if (humidity <= 40 && !isSendMsgToGateWay){  //湿度小于40开始喷水
//                    List<Device>deviceList = deviceService.selectDeviceByBlockId(Long.valueOf(deviceData.getDeviceid()));
                    List<Device>deviceList = deviceService.selectDeviceByBlockId(Long.valueOf(testblockid));
                    switchControl(deviceList,1);
                    System.out.println("西山湿度值过低发送打开开关命令，开始进行自动喷灌");
                    isSendMsgToGateWay = true;
                }
                if (humidity > 40 && isSendMsgToGateWay){
//                    List<Device>deviceList = deviceService.selectDeviceByBlockId(Long.valueOf(deviceData.getDeviceid()));
                    List<Device>deviceList = deviceService.selectDeviceByBlockId(Long.valueOf(testblockid));
                    switchControl(deviceList,0);
                    System.out.println("湿度值过高发送关闭开关，停止进行自动喷灌");
                    isSendMsgToGateWay = false;
                }
            }
        }
    }

    void switchControl(List<Device>deviceList,int powerStatus){
        for (Device device :deviceList){
            Instruction instruction = new Instruction();
            instruction.setAddress(device.getAddress());
            instruction.setChannel(23);
            instruction.setPowerSwitch(powerStatus);
            mqttPushClient.publishMessageToGateway(instruction);
        }
    }
}
