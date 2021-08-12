package com.libv.garden.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.Gardener;
import com.libv.entity.SignRecord;
import com.libv.entity.WorkTask;
import com.libv.garden.GardenerService;
import com.libv.mapper.GardenerMapper;
import com.libv.util.JwtUtil;
import com.libv.util.R;
import com.libv.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class GardenerImpl extends ServiceImpl<GardenerMapper, Gardener> implements GardenerService {

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Override
    public R addGardener(Gardener gardener, HttpServletRequest request) {
        int sex = gardener.getSex();
        if (!Validator.isMobile(gardener.getPhone()) || StrUtil.isBlank(gardener.getName())
                || null == gardener.getBirthday() || (sex != 1 && sex != 0)) {
            return R.error().message("添加信息有误");
        }
        Long gardenManagerId = JwtUtil.getIdFromRequest(request);
        if (null == gardenManagerId) {
            return R.error().message("缺少必要参数");
        }
        gardener.setPassword(DigestUtil.sha1Hex("123456"));
        gardener.setId(snowflakeUtil.getSnowflakeId());
        gardener.setGardenManagerId(gardenManagerId);
        try {
            baseMapper.insert(gardener);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("添加失败！请稍后再试");
        }
        return R.ok().message("添加成功");
    }

    @Override
    public R sign(HttpServletRequest request, String area) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id || StrUtil.isBlank(area)) {
            return R.error().message("缺少必要参数");
        }
        if (!this.checkIsExist(id)) {
            return R.error().message("不存在该园林工");
        }
        DateTime currentTime = new DateTime();
        String todayTime = currentTime.toString("yyyy-MM-dd HH:mm:ss");
        try {
            int isSigned = baseMapper.checkIsSigned(id);
            if (isSigned != 0) {
                return R.error().message("已经打卡");
            }
            baseMapper.sign(id, todayTime, area);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().message("打卡失败");
        }
        return R.ok().message("打卡成功");
    }

    @Override
    public boolean checkIsExist(long id) {
        Integer count = baseMapper.selectCount(new QueryWrapper<Gardener>().eq("id", id));
        return count == 1;
    }

    @Override
    public R login(String phone, String password) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(password)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<Gardener> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        Gardener gardener = baseMapper.selectOne(queryWrapper);
        if (null == gardener) {
            return R.error().message("账号或密码错误");
        }
        return R.ok().data("token", JwtUtil.getJwtToken(gardener.getId()));
    }

    @Override
    public R getAllTask(HttpServletRequest request, int start, int count) {
        if (start < 0 || count < 1) {
            return R.error().message("参数非法");
        }
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("缺少必要参数");
        }
        List<WorkTask> allTask = baseMapper.getAllTask(id, start, count);
        if (allTask.isEmpty()) {
            return R.error().message("任务为空");
        }
        return R.ok().data("task_list", allTask);
    }

    @Override
    public R getSignRecord(HttpServletRequest request, int start, int count) {
        if (start < 0 || count < 1) {
            return R.error().message("查询条件不合理");
        }
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("缺少必要参数");
        }
        List<SignRecord> recordList = baseMapper.getListSignRecord(id, start, count);
        return R.ok().data("sign_list", recordList);
    }

    @Override
    public R completeTask(HttpServletRequest request, long taskId) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("缺少必要参数");
        }
        if (baseMapper.checkTaskIsExist(taskId) == 0) {
            return R.error().message("任务不存在");
        }
        baseMapper.updateTaskStatus(taskId);
        return R.ok().message("修改成功");
    }
}
