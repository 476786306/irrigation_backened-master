package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.Gardener;
import com.libv.entity.SignRecord;
import com.libv.entity.WorkTask;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface GardenerMapper extends BaseMapper<Gardener> {
    int checkIsSigned(@Param("gardenerId") long gardenerId);

    void sign(@Param("gardenerId") long gardenerId, @Param("todayTime") String todayTime, @Param("area") String area);

    List<WorkTask> getAllTask(@Param("gardenerId") long id, @Param("start") int start, @Param("count") int count);

    List<SignRecord> getListSignRecord(@Param("gardenerId") long id, @Param("start") int start, @Param("count") int count);

    void updateTaskStatus(@Param("taskId") long taskId);

    int checkTaskIsExist(@Param("taskId")long taskId);


}
