package com.libv.garden;


import com.libv.entity.GardenManager;
import com.libv.entity.Gardener;
import com.libv.entity.WorkTask;
import com.libv.util.R;

import javax.servlet.http.HttpServletRequest;

public interface GardenManagerService {
    R login(String phone, String password);

    R getInfo(HttpServletRequest request);

    R publishTask(WorkTask workTask);

    R deleteGardenerById(long id);

    R getTaskByGardenerId(long id);

    R getAllGardenerInfo(HttpServletRequest request);

    R modifyGardenerInfo(Gardener gardener);

    R getGardenerSignRecord(long gardenerId, int start, int count);

    R getGardenAreaList(HttpServletRequest request);

    R getGardenAreaInfo(HttpServletRequest request, long managerId);

    R getGardenAreaBlockList(HttpServletRequest request);

    R getGardenAreaBlockInfo(Long id);

    R modifyGardenerMangerInfo(GardenManager gardenManager);

    R setLoginStatus(String phone);
}
