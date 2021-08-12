package com.libv.controller.garden;

import com.libv.garden.GardenerService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/gardener")
public class GardenerController {

    @Autowired
    GardenerService gardenerService;

    @PostMapping("/login")
    public R login(String phone, String password) {
        return gardenerService.login(phone, password);
    }

    /**
     * @param request 请求
     * @param area    打卡地址
     */
    @PostMapping("/sign")
    public R sign(HttpServletRequest request, String area) {
        return gardenerService.sign(request, area);
    }

    /**
     * 查询自己所有的任务
     *
     * @param request 请求
     * @param start   分页参数开始
     * @param count   分页参数结束
     */
    @GetMapping("/allTask")
    public R getAllTask(HttpServletRequest request, int start, int count) {
        return gardenerService.getAllTask(request, start, count);
    }

    /**
     * 查询所有上班打卡记录
     *
     * @param request 请求
     * @param start   分页参数开始
     * @param count   分页参数结束
     */
    @GetMapping("/getSignRecord")
    public R getSignRecord(HttpServletRequest request, int start, int count) {
        return gardenerService.getSignRecord(request, start, count);
    }

    /**
     * @param request 请求
     * @param taskId  任务唯一编号Id
     */
    @PutMapping("/taskStatus")
    public R completeTask(HttpServletRequest request, long taskId) {
        return gardenerService.completeTask(request, taskId);
    }

}

