package com.libv.garden.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.DeviceData;
import com.libv.entity.DeviceReservation;
import com.libv.entity.GateWay;
import com.libv.entity.GateWayData;
import com.libv.garden.DeviceDataService;
import com.libv.garden.DeviceSheduleService;
import com.libv.mapper.DeviceDataMapper;
import com.libv.mapper.DeviceMapper;
import com.libv.mapper.DeviceSheduleMapper;
import com.libv.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceDataImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements DeviceDataService {

    @Override
    public R addSubDeviceData(DeviceData deviceData) {
        if (deviceData != null) {
            try {
                baseMapper.insert(deviceData);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return  R.error();
            }
        }
        return R.ok();
    }

    @Override
    public DeviceData selectLatestDeviceData() {
        return baseMapper.selectLatestDeviceData();
    }


    @Override
    public R selectDeviceDataByBlockid(long blockid) {
        DeviceData deviceHumidityData = baseMapper.selectDeviceHumidityByBlockid(blockid);
        GateWayData gateWayData = new GateWayData();
        if(deviceHumidityData != null) {
            gateWayData.setId(deviceHumidityData.getDeviceid());
            gateWayData.setAddress(deviceHumidityData.getAddress());
            gateWayData.setChannel(deviceHumidityData.getChannel());
            gateWayData.setTime(deviceHumidityData.getSystemtime());
            gateWayData.setHumidity(deviceHumidityData.getHumidity());
            gateWayData.setTemperture(deviceHumidityData.getTemperature());
            DeviceData deviceNitrogenyData = baseMapper.selectDeviceNitrogenyBlockid(blockid);
            gateWayData.setPhosphorus(deviceNitrogenyData.getPhosphorus());
            gateWayData.setNitrogen(deviceNitrogenyData.getNitrogen());
            gateWayData.setPotassium(deviceNitrogenyData.getPotassium());
        }
        return R.ok().data("machine_data",gateWayData);
    }

    @Override
    public DeviceData selectDeviceNitrogenyBlockid(long blockid) {
        return baseMapper.selectDeviceNitrogenyBlockid(blockid);
    }

    @Override
    public DeviceData selectDeviceHumidityByBlockid(long blockid) {
        return baseMapper.selectDeviceHumidityByBlockid(blockid);
    }


}
