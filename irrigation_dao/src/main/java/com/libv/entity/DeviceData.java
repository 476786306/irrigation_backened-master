package com.libv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("device_data")
public class DeviceData {

  private long id;
  private String deviceid;
  private String address;
  private String channel;
  private String humidity;
  private String temperature;
  private String nitrogen;
  private String phosphorus;
  private String potassium;
  private String pressure;
  private String gatetime;
  private String status;
  private String systemtime;

}
