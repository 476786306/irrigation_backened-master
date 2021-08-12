package com.libv.garden;

import com.libv.entity.DeviceReservation;
import com.libv.util.R;

import java.util.List;

public interface DeviceSheduleService {
    R addSwitchControlShedule(DeviceReservation deviceReservation);

    R setSwitchControlShedule(int resverstatus,long id);

    List<DeviceReservation> selectSwitchControlShedule();
}
