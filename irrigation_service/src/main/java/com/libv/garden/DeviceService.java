package com.libv.garden;

import com.libv.entity.Device;
import com.libv.util.R;

import java.util.List;

public interface DeviceService {
    R setSwitchStatus(long blockid, long deviceid,Integer devicestatus);

    R setSwitchStatusByDeviceId(long deviceid,Integer devicestatus);

    R setSwitchControlTime(long blockid, long deviceid,String controltime);

    List<Device>selectDeviceByBlockId(long blocid);
}
