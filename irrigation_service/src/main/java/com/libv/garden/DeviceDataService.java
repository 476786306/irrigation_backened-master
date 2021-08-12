package com.libv.garden;

import com.libv.entity.DeviceData;
import com.libv.util.R;

public interface DeviceDataService {
    R  addSubDeviceData(DeviceData deviceData);

    R  selectDeviceDataByBlockid(long blockid);

    DeviceData  selectLatestDeviceData();

    DeviceData selectDeviceHumidityByBlockid(long blockid);

    DeviceData selectDeviceNitrogenyBlockid(long blockid);

}
