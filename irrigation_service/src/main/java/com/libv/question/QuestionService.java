package com.libv.question;

import com.libv.entity.Question;
import com.libv.util.R;

public interface QuestionService {
    R addQuestion(Question question, String imageUrl);

    R getQuestionList(int start, int count);

    R getQuestionPreview(int start, int count);
}
