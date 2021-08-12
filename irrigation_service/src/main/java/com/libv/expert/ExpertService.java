package com.libv.expert;

import com.libv.util.R;

import javax.servlet.http.HttpServletRequest;

public interface ExpertService {
    R getExpertInfoById(HttpServletRequest request);

    R login(String phone, String password);

    R changePassword(String phone, String password, String newpassword);

    R viewListQuestion(HttpServletRequest request, int start, int count);
}
