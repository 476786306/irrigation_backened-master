package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.dto.GardenAreaBlockDTO;
import com.libv.entity.*;
import jdk.nashorn.internal.ir.Block;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GardenManagerMapper extends BaseMapper<GardenManager> {
    void addTask(WorkTask workTask);

    int selectGardenerById(long id);

    void deleteGardenerById(long id);

    List<WorkTask> getListTaskByGardenerId(long id);

    List<Gardener> getListGardenerInfo(@Param("gardenManagerId") long id);

    void modifyGardenerInfo(Gardener gardener);

    void modifyGardenerMangerInfo(GardenManager gardenManager);

    List<SignRecord> getGardenerSignRecord(@Param("gardenerId") long gardenerId,
                                           @Param("start") int start, @Param("count") int count);

    List<GardenArea> getGardenAreaList(@Param("managerId") long managerId);

    @Select("select * from garden_area where id in(select garden_area_id from garden_manager_garden_area where manager_id = #{managerId})")
    List<GardenArea> getGardenAreaInfo(@Param("managerId") long managerId);

    List<GardenAreaBlockDTO> getGardenAreaBlockList(@Param("managerId") Long managerId);

    List<Device> getGardenAreaBlockDeviceInfo(@Param("blockId") Long blockId);

    GardenAreaBlock getGardenAreaBlockInfo(@Param("blockId") Long id);

    List<GardenAreaBlock> getBlockInfoByGardenid(@Param("gardenId") Long gardenId);

    void updateLoginStatus(@Param("phone")String phone,@Param("loginStatus")int loginStatus);

    GardenManager selectMangerByPhone(@Param("phone")String phone);
}
