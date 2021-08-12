package com.libv.garden.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.dto.GardenAreaBlockDTO;
import com.libv.dto.GardenAreaBlockDetailDTO;
import com.libv.entity.*;
import com.libv.garden.GardenManagerService;
import com.libv.mapper.GardenManagerMapper;
import com.libv.util.JwtUtil;
import com.libv.util.R;
import com.libv.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GardenManagerImpl extends ServiceImpl<GardenManagerMapper, GardenManager> implements GardenManagerService {

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Autowired
    GardenManagerService gardenManagerService;

    @Override
    public R login(String phone, String password) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(password)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<GardenManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        GardenManager manager = baseMapper.selectOne(queryWrapper);
        if (null == manager) {
            return R.error().message("管理员不存在或密码错误");
        }
        if(manager.getLoginStatus() == 1){
            return R.error().message("该账号已经登录不能进行重复登录");
        }else{
            int loginStatus = 1;
            baseMapper.updateLoginStatus(phone,loginStatus);
        }
        log.info(phone + " 管理员登录成功");
        return R.ok().data("token", JwtUtil.getJwtToken(manager.getId()))
                .data("user_identity", manager.getIdentity());
    }

    @Override
    public R getInfo(HttpServletRequest request) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("必要参数缺失");
        }
        QueryWrapper<GardenManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        GardenManager gardenManager = baseMapper.selectOne(queryWrapper);
        if (null == gardenManager) {
            return R.error().message("查询不到个人信息");
        }
        String formatBirthday = DateUtil.format(gardenManager.getBirthday(), "yyyy-MM-dd");
        return R.ok()
                .data("name", gardenManager.getName()).data("birthday", formatBirthday)
                .data("sex", gardenManager.getSex()).data("phone", gardenManager.getPhone());
    }

    @Override
    public R publishTask(WorkTask workTask) {
        if (null == workTask.getTaskContent() || 0 == workTask.getGardenerId()) {
            return R.error().message("参数缺失");
        }
        workTask.setId(snowflakeUtil.getSnowflakeId());
        workTask.setStartTime(new Timestamp(new Date().getTime()));
        try {
            baseMapper.addTask(workTask);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("发布任务失败！请稍后再试");
        }
        return R.ok().message("发布任务成功");
    }

    @Override
    public R deleteGardenerById(long id) {
        int count = baseMapper.selectGardenerById(id);
        if (count == 0) {
            return R.error().message("园林工不存在");
        }
        baseMapper.deleteGardenerById(id);
        return R.ok().message("删除成功");
    }

    @Override
    public R getTaskByGardenerId(long id) {
        List<WorkTask> taskList = baseMapper.getListTaskByGardenerId(id);
        if (taskList.isEmpty()) {
            return R.error().message("查询不到信息");
        } else {
            return R.ok().data("task_list", taskList);
        }
    }

    @Override
    public R getAllGardenerInfo(HttpServletRequest request) {
        Long managerId = JwtUtil.getIdFromRequest(request);
        if (null == managerId) {
            return R.error().message("缺少必要参数");
        }
        List<Gardener> listGardenerInfo = baseMapper.getListGardenerInfo(managerId);
        if (listGardenerInfo.isEmpty()) {
            return R.error().message("查询不到信息");
        } else {
            return R.ok().data("info_list", listGardenerInfo);
        }
    }

    @Override
    public R modifyGardenerInfo(Gardener gardener) {
        if (StrUtil.isNotBlank(gardener.getPassword())) {
            gardener.setPassword(DigestUtil.sha1Hex(gardener.getPassword()));
        }
        if (gardener.getId() == 0) {
            return R.error().message("参数缺失");
        }
        try {
            baseMapper.modifyGardenerInfo(gardener);
            return R.ok().message("修改成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("修改失败");
        }
    }


    @Override
    public R getGardenerSignRecord(long gardenerId, int start, int count) {
        if (start < 0 || count < 1) {
            return R.error().message("参数非法");
        }
        List<SignRecord> recordList = baseMapper.getGardenerSignRecord(gardenerId, start, count);
        if (recordList.isEmpty()) {
            return R.error().message("打卡列表为空");
        }
        return R.ok().data("sign_list", recordList);
    }

    @Override
    public R getGardenAreaList(HttpServletRequest request) {
        Long managerId = JwtUtil.getIdFromRequest(request);
        if (null == managerId) {
            return R.error().message("缺少必要参数");
        }
        List<GardenArea> gardenAreaList = baseMapper.getGardenAreaList(managerId);
        return R.ok().data("garden_area_list", gardenAreaList);
    }

    @Override
    public R getGardenAreaInfo(HttpServletRequest request, long managerId) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("缺少必要参数");
        }
        List<GardenArea> gardenAreaInfo = baseMapper.getGardenAreaInfo(managerId);
        return R.ok().data("garden_area_List", gardenAreaInfo);
    }

    @Override
    public R getGardenAreaBlockList(HttpServletRequest request) {
        Long managerId = JwtUtil.getIdFromRequest(request);
        if (null == managerId) {
            return R.error().message("缺少必要参数");
        }
        List<GardenAreaBlockDTO> gardenAreaBlockList = baseMapper.getGardenAreaBlockList(managerId);
        return R.ok().data("garden_area_block_list", gardenAreaBlockList);
    }

    @Override
    public R getGardenAreaBlockInfo(Long id) {
        GardenAreaBlock gardenAreaBlock = baseMapper.getGardenAreaBlockInfo(id);
        if (gardenAreaBlock == null) {
            return R.error().message("块区不存在");
        }
        List<Device> gardenAreaBlockDeviceInfoList = baseMapper.getGardenAreaBlockDeviceInfo(id);
        if (gardenAreaBlockDeviceInfoList.isEmpty()) {
            return R.error().message("查询块设备数据为空");
        }
        GardenAreaBlockDetailDTO gardenAreaBlockDetailDTO = new GardenAreaBlockDetailDTO();
        gardenAreaBlockDetailDTO.setBlockId(id);
        gardenAreaBlockDetailDTO.setBlockName(gardenAreaBlock.getBlockName());
        gardenAreaBlockDetailDTO.setStatus(gardenAreaBlock.getBlockStatus());
        gardenAreaBlockDetailDTO.setDeviceList(gardenAreaBlockDeviceInfoList);
        return R.ok().data("garden_area_block_info", gardenAreaBlockDetailDTO);
    }

    @Override
    public R modifyGardenerMangerInfo(GardenManager gardenManager) {
        if (StrUtil.isNotBlank(gardenManager.getPassword())) {
            gardenManager.setPassword(DigestUtil.sha1Hex(gardenManager.getPassword()));
        }
        if (StringUtils.isEmpty(gardenManager.getPhone())) {
            return R.error().message("参数缺失");
        }
        try {
            baseMapper.modifyGardenerMangerInfo(gardenManager);
            return R.ok().message("修改成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("修改失败");
        }
    }

    @Override
    public R setLoginStatus(String phone) {
        int loginStatus = 0;
        baseMapper.updateLoginStatus(phone,loginStatus);
        return R.ok();
    }

}
