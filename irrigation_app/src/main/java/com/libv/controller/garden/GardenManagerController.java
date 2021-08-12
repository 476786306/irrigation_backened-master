package com.libv.controller.garden;

import com.libv.entity.GardenManager;
import com.libv.entity.Gardener;
import com.libv.entity.WorkTask;
import com.libv.garden.GardenManagerService;
import com.libv.garden.GardenerService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/gardenManager")
public class GardenManagerController {

    @Autowired
    GardenerService gardenerService;

    @Autowired
    GardenManagerService gardenManagerService;


    @PostMapping("/login")
    public R login(String phone, String password) {

        return gardenManagerService.login(phone, password);
    }


    @PostMapping("/setLoginStatus")
    public R setLoginStatus(String phone) {
        return gardenManagerService.setLoginStatus(phone);
    }
    @GetMapping("/info")
    public R getManagerInfo(HttpServletRequest request) {
        return gardenManagerService.getInfo(request);
    }

    @PostMapping("/gardener")
    public R addGardener(@RequestBody Gardener gardener, HttpServletRequest request) {
        return gardenerService.addGardener(gardener, request);
    }

    @PostMapping("/task")
    public R publishTask(@RequestBody WorkTask workTask) {
        return gardenManagerService.publishTask(workTask);
    }

    @DeleteMapping("/gardener/{id}")
    public R deleteGardenerById(@PathVariable long id) {
        return gardenManagerService.deleteGardenerById(id);
    }

    @GetMapping("/taskForGardener/{id}")
    public R getTaskByGardenerId(@PathVariable long id) {
        return gardenManagerService.getTaskByGardenerId(id);
    }

    @GetMapping("/allGardener")
    public R getAllGardenerInfo(HttpServletRequest request) {
        return gardenManagerService.getAllGardenerInfo(request);
    }

    @PutMapping("/gardener")
    public R modifyGardenerInfo(@RequestBody Gardener gardener) {
        return gardenManagerService.modifyGardenerInfo(gardener);
    }

    @GetMapping("/gardenerSignRec/{id}")
    public R getGardenerSignRecord(@PathVariable("id") long gardenerId, int start, int count) {
        return gardenManagerService.getGardenerSignRecord(gardenerId, start, count);
    }

    @GetMapping("/gardenArea")
    public R getGardenArea(HttpServletRequest request) {
        return gardenManagerService.getGardenAreaList(request);
    }

    @GetMapping("/gardenAreaInfo")
    public R getGardenAreaInfo(HttpServletRequest request, long managerId) {
        return gardenManagerService.getGardenAreaInfo(request, managerId);
    }

    @GetMapping("/gardenAreaBlock")
    public R getGardenAreaBlock(HttpServletRequest request) {
        return gardenManagerService.getGardenAreaBlockList(request);
    }

    @GetMapping("/gardenAreaBlockInfo/{id}")
    public R getGardenAreaBlockInfo(@PathVariable Long id) {
        return gardenManagerService.getGardenAreaBlockInfo(id);
    }

    @PutMapping("/gardenerMangerInfo")
    public R modifyGardenerMangerInfo(@RequestBody GardenManager gardenManager) {
        return gardenManagerService.modifyGardenerMangerInfo(gardenManager);
    }
}
