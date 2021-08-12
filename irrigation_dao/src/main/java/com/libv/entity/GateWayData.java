package com.libv.entity;

import lombok.Data;

@Data
public class GateWayData {
    String id;
    String address; //0 0 X 0 第3位X代表不同的块区
    String channel; //
    String time;
    String number; //
    String powerSwitch;
    String humidity; //湿度
    String temperture; //温度 拼错了 没办法
    String nitrogen; //氮磷钾
    String phosphorus;
    String potassium;
    String pressure; //压力
    String regulate; //调节阀状态
    String status; //报错数据包
}