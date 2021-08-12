package com.libv.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import java.sql.Timestamp;

@Data
@TableName("device_reservation")
public class DeviceReservation {

  @GeneratedValue
  private Long id;

  private Long deviceId;

  private String address;

  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private  Timestamp resverTime;

  private int  resverStatus;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Timestamp createTime;


  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private  Timestamp stopTime;



}
