package com.libv.dto;

import lombok.Data;

/**
 * @author longjie
 * @since 2021/4/26 14:05
 */
@Data
public class DeviceDTO {
    private Long id;

    private String deviceName;

    private String deviceStatus;

    private int deviceType;

    private String address;
}
