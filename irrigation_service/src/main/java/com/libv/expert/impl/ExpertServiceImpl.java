package com.libv.expert.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.Expert;
import com.libv.entity.Question;
import com.libv.expert.ExpertService;
import com.libv.mapper.ExpertMapper;
import com.libv.util.JwtUtil;
import com.libv.util.R;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertMapper, Expert> implements ExpertService {
    @Override
    public R getExpertInfoById(HttpServletRequest request) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id) {
            return R.error().message("缺少必要参数");
        }
        QueryWrapper<Expert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.select("id", "name", "phone", "sex", "birthday", "major", "avatar");
        Expert expert = baseMapper.selectOne(queryWrapper);
        if (null == expert) {
            return R.error().message("查询不到专家信息");
        }
        return R.ok().data("info", expert);
    }

    @Override
    public R login(String phone, String password) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(password)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<Expert> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        Expert expert = baseMapper.selectOne(loginWrapper);
        if (null == expert) {
            return R.error().message("账号或密码错误");
        }
        return R.ok().data("token", JwtUtil.getJwtToken(expert.getId()));
    }

    @Override
    public R changePassword(String phone, String password, String newpassword) {
        if (!Validator.isMobile(phone) || StrUtil.isBlank(password) || StrUtil.isBlank(newpassword)) {
            return R.error().message("参数非法");
        }
        QueryWrapper<Expert> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq("phone", phone).eq("password", DigestUtil.sha1Hex(password));
        Expert expert = baseMapper.selectOne(loginWrapper);
        if (null == expert) {
            return R.error().message("账号或密码错误");
        }
        expert.setPassword(DigestUtil.sha1Hex(newpassword));
        newpassword = DigestUtil.sha1Hex(newpassword);
        baseMapper.changePassword(phone, password , newpassword);
        return R.ok().data("密码修改成功，密码", expert.getPassword());
    }

    @Override
    public R viewListQuestion(HttpServletRequest request, int start, int count) {
        Long id = JwtUtil.getIdFromRequest(request);
        if (null == id || start < 0 || count < 1) {
            return R.error().message("参数非法");
        }
        List<Question> questionList = baseMapper.viewAllQuestion(id, start, count);
        if (questionList.isEmpty()) {
            return R.error().message("问题列表为空");
        }
        return R.ok().data("question_list", questionList);
    }
}
