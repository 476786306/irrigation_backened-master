package com.libv.gateway.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.GateWay;
import com.libv.gateway.GateWayService;
import com.libv.mapper.GateWayMapper;
import com.libv.util.R;
import org.springframework.stereotype.Service;

@Service
public class GateWayServiceImpl extends ServiceImpl<GateWayMapper, GateWay> implements GateWayService {

    @Override
    public R addGateway(long id) {
        if (id <= 0) {
            return R.error().message("网关Id错误");
        }
        GateWay one = baseMapper.selectById(id);
        if (null != one) {
            return R.error().message("此网关已存在");
        }
        try {
            GateWay gateWay = new GateWay();
            gateWay.setId(id);
            baseMapper.insert(gateWay);
            return R.ok().message("添加网关成功");
        } catch (Exception e) {
            return R.error().message("添加网关错误");
        }
    }
}
