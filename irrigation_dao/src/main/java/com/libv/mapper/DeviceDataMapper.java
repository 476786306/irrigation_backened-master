package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.DeviceData;
import com.libv.util.R;
import org.apache.ibatis.annotations.Param;

public interface DeviceDataMapper extends BaseMapper<DeviceData> {
    DeviceData  selectLatestDeviceData();

    DeviceData selectDeviceHumidityByBlockid(@Param("blockid")long blockid);

    DeviceData selectDeviceNitrogenyBlockid(@Param("blockid")long blockid);
}
