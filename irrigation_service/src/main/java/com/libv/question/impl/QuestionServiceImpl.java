package com.libv.question.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libv.entity.Question;
import com.libv.mapper.QuestionMapper;
import com.libv.question.QuestionService;
import com.libv.util.R;
import com.libv.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Override
    public R addQuestion(Question question, String imageUrl) {
        if (!StrUtil.isAllNotBlank(question.getVariety(), question.getDetail())) {
            return R.error().message("问题参数缺失");
        }
        if (StrUtil.isNotBlank(imageUrl)) {
            question.setPictureUrl(imageUrl);
        }
        question.setId(snowflakeUtil.getSnowflakeId());
        try {
            baseMapper.insert(question);
        } catch (Exception e) {
            log.error("添加问题失败：" + e.getMessage());
            return R.error().message("添加问题失败");
        }
        return R.ok().message("添加问题成功");
    }

    @Override
    public R getQuestionList(int start, int count) {
        if (start <= 0 || count <= 0) {
            return R.error().message("分页参数错误");
        }
        Page<Question> questionPage = baseMapper.selectPage(new Page<>(start, count), null);
        List<Question> questions = questionPage.getRecords();
        if (questions.isEmpty()) {
            return R.error().message("查询不到问题信息");
        }
        return R.ok().data("question_list", questions);
    }

    @Override
    public R getQuestionPreview(int start, int count) {
        if (start <= 0 || count <= 0) {
            return R.error().message("分页参数错误");
        }
        QueryWrapper<Question> previewWrapper = new QueryWrapper<>();
        previewWrapper.select("variety","detail","picture_url","create_time","questioner_id");
        Page<Question> questionPage = baseMapper.selectPage(new Page<>(start, count), previewWrapper);
        List<Question> questions = questionPage.getRecords();
        if (questions.isEmpty()) {
            return R.error().message("查询不到问题信息");
        }
        return R.ok().data("question_preview", questions);
    }
}
