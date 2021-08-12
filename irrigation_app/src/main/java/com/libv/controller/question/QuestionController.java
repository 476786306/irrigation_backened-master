package com.libv.controller.question;

import com.libv.entity.Question;
import com.libv.question.QuestionService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/new")
    public R addQuestion(@RequestBody Question question, String imageUrl) {
        return questionService.addQuestion(question, imageUrl);
    }

    @GetMapping("/all")
    public R getListQuestion(int start, int count) {
        return questionService.getQuestionList(start, count);
    }

    @GetMapping("/preview")
    public R getPreviewQuestion(int start, int count) {
        return questionService.getQuestionPreview(start, count);
    }
}
