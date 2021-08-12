package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.Admin;
import com.libv.entity.Expert;
import com.libv.entity.GardenManager;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    @Insert("insert into garden_manager(id,name,phone) values(#{id},#{name},#{phone})")
    void addManager(@Param("id") long id, @Param("name") String name, @Param("phone") String phone);

    @Delete("delete from garden_manager where id=#{managerId}")
    void deleteManagerById(@Param("managerId") long managerId);

    @Select("select id,name,phone,sex,birthday,admin_id,avatar from garden_manager where admin_id=#{adminId}")
    List<GardenManager> getAllManagerById(@Param("adminId") long adminId);

    void addExpert(Expert expert);

    @Select("select id,name,phone,sex,birthday,major,avatar from expert where admin_id=#{adminId} ")
    List<Expert> getAllExpertById(Long adminId);
}
