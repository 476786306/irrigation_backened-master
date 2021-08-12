package com.libv.controller.garden;


import com.libv.entity.DeviceReservation;
import com.libv.garden.DeviceService;
import com.libv.garden.GardenerService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/setSwitchStatus")
    public R setSwitchStatus(long blockid, long deviceid, Integer devicestatus){
        return deviceService.setSwitchStatus(blockid,deviceid,devicestatus);
    }


    @PostMapping("/setSwitchControlTime")
    public R setSwitchControlTime(long blockid, long deviceid, String  controltime){
        return deviceService.setSwitchControlTime(blockid,deviceid,controltime);
    }

    @PostMapping("/selectDeviceByBlockId")
    public R selectDeviceByBlockId(long blockid){
        return R.ok();
    }


}
