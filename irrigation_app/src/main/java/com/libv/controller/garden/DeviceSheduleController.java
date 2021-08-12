package com.libv.controller.garden;


import com.libv.entity.DeviceReservation;
import com.libv.garden.DeviceSheduleService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@CrossOrigin
@RequestMapping("/deviceShedule")
public class DeviceSheduleController {

    @Autowired
    DeviceSheduleService deviceSheduleService;

    @PostMapping("/addSwitchControlShedule")
    public R addSwitchControlShedule(@RequestBody DeviceReservation deviceReservation){
        return deviceSheduleService.addSwitchControlShedule(deviceReservation);
    }


    @GetMapping("/setSwitchControlShedule")
    public R setSwitchControlShedule(){
        return deviceSheduleService.setSwitchControlShedule(1,203302322);
    }

}
