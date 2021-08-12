package com.libv.mqtt.entity;

import lombok.Data;

@Data
public class Instruction {
    int PowerSwitch;
    String address;
    int channel;

}
