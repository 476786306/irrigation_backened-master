package com.libv.controller.admin;

import com.libv.admin.AdminService;
import com.libv.entity.Expert;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 总经理登录
     *
     * @param phone    手机号
     * @param password 密码
     */
    @PostMapping("/login")
    public R login(String phone, String password) {
        return adminService.login(phone, password);
    }

    /**
     * 添加管理员
     *
     * @param name  管理员姓名
     * @param phone 管理员手机号
     */
    @PostMapping("/manager")
    public R addManager(String name, String phone) {
        return adminService.addManager(name, phone);
    }

    /**
     * 删除管理员
     *
     * @param managerId 管理员ID
     */
    @DeleteMapping("/manager/{id}")
    public R deleteManager(@PathVariable("id") long managerId) {
        return adminService.deleteManagerById(managerId);
    }

    /**
     * 获取所有管理员信息
     */
    @GetMapping("/allManager")
    public R getAllManager(HttpServletRequest request) {
        return adminService.getAllManagerInfo(request);
    }

    /**
     * 获取所有专家信息
     */
    @GetMapping("/allExpert")
    public R getAllExpert(HttpServletRequest request) {
        return adminService.getAllExpertInfo(request);
    }

    /**
     * 添加专家
     *
     * @param expert 专家相关信息实体
     */
    @PostMapping("/expert")
    public R addExpert(HttpServletRequest request, @RequestBody Expert expert) {
        return adminService.addExpert(request, expert);
    }

}
