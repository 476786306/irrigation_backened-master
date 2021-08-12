package com.libv.controller.garden;


import com.libv.entity.Device;
import com.libv.entity.DeviceData;
import com.libv.garden.DeviceDataService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/devicedata")
public class DeviceDataController {
    @Autowired
    DeviceDataService deviceDataService;

    @GetMapping("/addSubDeviceData")
    public R addSubDeviceData(DeviceData deviceData){
        if(deviceDataService == null) System.out.println("service为null");
        return deviceDataService.addSubDeviceData(deviceData);
    }

    @PostMapping("/selectDeviceDataByBlockid")
    public R selectDeviceDataByBlockid(long blockid){
        return deviceDataService.selectDeviceDataByBlockid(blockid);
    }


}
