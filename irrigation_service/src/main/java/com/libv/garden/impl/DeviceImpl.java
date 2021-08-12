package com.libv.garden.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.Device;
import com.libv.garden.DeviceService;
import com.libv.mapper.DeviceMapper;
import com.libv.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeviceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService{
    @Override
    public R setSwitchStatus(long blockid, long deviceid, Integer devicestatus) {
        baseMapper.setSwitchStatus(blockid,deviceid,devicestatus);
        return R.ok();
    }

    @Override
    public R setSwitchStatusByDeviceId(long deviceid, Integer devicestatus) {
        baseMapper.setSwitchStatusByDeviceId(deviceid,devicestatus);
        return R.ok();
    }

    @Override
    public R setSwitchControlTime(long blockid, long deviceid, String controltime) {
        return R.ok();
    }

    @Override
    public List<Device> selectDeviceByBlockId(long blockid) {
        List<Device>deviceList = baseMapper.selectDeviceByBlockId(blockid);
        return deviceList;
    }
}
