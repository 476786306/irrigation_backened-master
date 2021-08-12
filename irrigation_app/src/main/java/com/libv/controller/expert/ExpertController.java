package com.libv.controller.expert;

import com.libv.expert.ExpertService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/expert")
public class ExpertController {

    @Autowired
    ExpertService expertService;

    @GetMapping("/info")
    public R getExpertInfo(HttpServletRequest request) {
        return expertService.getExpertInfoById(request);
    }

    @PostMapping("/login")
    public R login(String phone, String password) {
        return expertService.login(phone, password);
    }

    @PostMapping("/changepassword")
    public R changePassword(String phone, String password, String newpassword) {
        return expertService.changePassword(phone, password, newpassword);
    }

    @GetMapping("/allQuestion")
    public R viewAllQuestion(HttpServletRequest request, int start, int count) {
        return expertService.viewListQuestion(request, start, count);
    }
}
