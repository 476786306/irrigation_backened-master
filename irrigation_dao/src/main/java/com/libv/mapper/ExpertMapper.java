package com.libv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libv.entity.Expert;
import com.libv.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ExpertMapper extends BaseMapper<Expert> {

    @Update("update expert set password = #{newpassword} where phone = #{phone}")
    void changePassword(@Param("phone") String phone, @Param("password") String password, @Param("newpassword") String newpassword);

    List<Question> viewAllQuestion(@Param("expertId") long id, @Param("start") int start, @Param("count") int count);
}
