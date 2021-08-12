package com.libv.dto;

import com.libv.entity.Device;
import lombok.Data;

import java.util.List;

/**
 * @author longjie
 * @since 2021/4/26 7:48
 */
@Data
public class GardenAreaBlockDetailDTO {
    private Long blockId;
    private String blockName;
    private int status;
    private List<Device> deviceList;
}
