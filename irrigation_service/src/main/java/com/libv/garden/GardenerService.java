package com.libv.garden;

import com.libv.entity.Gardener;
import com.libv.util.R;

import javax.servlet.http.HttpServletRequest;

public interface GardenerService {
    R addGardener(Gardener gardener,HttpServletRequest request);

    R sign(HttpServletRequest request, String area);

    boolean checkIsExist(long id);

    R login(String phone, String password);

    R getAllTask(HttpServletRequest request, int start, int count);

    R getSignRecord(HttpServletRequest request, int start, int count);

    R completeTask(HttpServletRequest request, long taskId);
}
