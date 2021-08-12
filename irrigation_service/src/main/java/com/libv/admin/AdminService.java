package com.libv.admin;

import com.libv.entity.Expert;
import com.libv.util.R;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    R login(String phone, String password);

    R addManager(String name, String phone);

    R deleteManagerById(long managerId);

    R getAllManagerInfo(HttpServletRequest request);

    R addExpert(HttpServletRequest request, Expert expert);

    R getAllExpertInfo(HttpServletRequest request);
}
