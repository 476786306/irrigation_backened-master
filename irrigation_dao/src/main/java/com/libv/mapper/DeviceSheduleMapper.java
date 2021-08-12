package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.DeviceReservation;
import com.libv.entity.Expert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceSheduleMapper extends BaseMapper<DeviceReservation> {
    void addSwitchControlShedule(DeviceReservation deviceReservation);

    List<DeviceReservation>selectSwitchControlShedule();

    void setSwitchControlSheduleStatus(@Param("resverstatus")int resverstatus, @Param("id")long id);
}
