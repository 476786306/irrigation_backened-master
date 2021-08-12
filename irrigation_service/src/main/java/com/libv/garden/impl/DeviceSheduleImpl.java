package com.libv.garden.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.DeviceReservation;
import com.libv.garden.DeviceSheduleService;
import com.libv.mapper.DeviceSheduleMapper;
import com.libv.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class DeviceSheduleImpl extends ServiceImpl<DeviceSheduleMapper, DeviceReservation> implements DeviceSheduleService {
    @Override
    public R addSwitchControlShedule(DeviceReservation deviceReservation) {
        if(deviceReservation.getDeviceId() == null){
            return R.error().message("缺少必要参数");
        }
        Date date = new Date(deviceReservation.getResverTime().getTime());
        Date stopDate = new Date(date.getTime() + 1 * 1 * 20 * 20 * 1000);
        Timestamp stopTimeStamp = new Timestamp(stopDate.getTime());
        deviceReservation.setStopTime(stopTimeStamp);
        deviceReservation.setCreateTime(new Timestamp(new Date().getTime()));
        baseMapper.addSwitchControlShedule(deviceReservation);
        return R.ok();
    }

    @Override
    public R setSwitchControlShedule(int status,long deviceid) {
        baseMapper.setSwitchControlSheduleStatus(status,deviceid);
        return R.ok();
    }

    @Override
    public  List<DeviceReservation> selectSwitchControlShedule() {
        List<DeviceReservation>deviceReservationList = baseMapper.selectSwitchControlShedule();
        return deviceReservationList;
    }
}
