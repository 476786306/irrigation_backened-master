package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.Device;
import com.libv.entity.Expert;
import com.libv.entity.GardenManager;
import com.libv.util.R;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface DeviceMapper extends BaseMapper<Device> {

    void setSwitchStatus(@Param("blockid") long blockid, @Param("deviceid") long deviceid, @Param("devicestatus") int devicestatus);

    void setSwitchStatusByDeviceId(@Param("deviceid") long deviceid, @Param("devicestatus") int devicestatus);

    List<Device>selectDeviceByBlockId(@Param("blockid")long blockid);
}
