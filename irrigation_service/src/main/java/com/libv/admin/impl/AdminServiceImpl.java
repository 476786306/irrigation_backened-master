package com.libv.admin.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.admin.AdminService;
import com.libv.entity.Admin;
import com.libv.entity.Expert;
import com.libv.entity.GardenManager;
import com.libv.mapper.AdminMapper;
import com.libv.util.JwtUtil;
import com.libv.util.R;
import com.libv.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Override
    public R login(String phone, String password) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(password)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        Admin admin = baseMapper.selectOne(queryWrapper);
        if (null == admin) {
            return R.error().message("手机号或密码错误");
        }
        return R.ok().data("token", JwtUtil.getJwtToken(admin.getId()));
    }

    @Override
    public R addManager(String name, String phone) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(name)) {
            return R.error().message("参数非法");
        }
        long snowflakeId = snowflakeUtil.getSnowflakeId();
        try {
            baseMapper.addManager(snowflakeId, name, phone);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("添加园林管理员失败！请稍后再试");
        }
        return R.ok().message("添加成功");
    }

    @Override
    public R deleteManagerById(long managerId) {
        baseMapper.deleteManagerById(managerId);
        return R.ok().message("删除成功");
    }

    @Override
    public R getAllManagerInfo(HttpServletRequest request) {
        Long adminId = JwtUtil.getIdFromRequest(request);
        if (null == adminId) {
            return R.error().message("必要参数缺失");
        }
        List<GardenManager> managerList = baseMapper.getAllManagerById(adminId);
        if (managerList.isEmpty()) {
            return R.error().message("查询不到所管辖园林管理员信息");
        }
        return R.ok().data("manager_list", managerList);
    }

    @Override
    public R addExpert(HttpServletRequest request, Expert expert) {
        Long adminId = JwtUtil.getIdFromRequest(request);
        if (null == adminId) {
            return R.error().message("必要参数缺失");
        }
        if (StrUtil.isBlank(expert.getName()) || !Validator.isMobile(expert.getPhone())
                || StrUtil.isBlank(expert.getMajor())
                || null == expert.getBirthday()) {
            return R.error().message("参数非法");
        }
        expert.setId(snowflakeUtil.getSnowflakeId());
        expert.setPassword(DigestUtil.sha1Hex("123"));
        expert.setAdminId(adminId);
        try {
            baseMapper.addExpert(expert);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("添加专家失败");
        }
        return R.ok().message("添加专家成功");
    }

    @Override
    public R getAllExpertInfo(HttpServletRequest request) {
        Long adminId = JwtUtil.getIdFromRequest(request);
        if (null == adminId) {
            return R.error().message("必要参数缺失");
        }
        List<Expert> expertList = baseMapper.getAllExpertById(adminId);
        if (expertList.isEmpty()) {
            return R.error().message("查询不到所管辖专家信息");
        }
        return R.ok().data("expert_list", expertList);
    }
}
