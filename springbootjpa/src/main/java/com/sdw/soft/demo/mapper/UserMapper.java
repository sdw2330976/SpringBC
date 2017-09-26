package com.sdw.soft.demo.mapper;

import com.sdw.soft.demo.vo.User;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * Created by shangyd on 2017/9/24.
 */
@Mapper
public interface UserMapper {


    @Select("select * from User where user_name = #{userName}")
    public User findByName(@Param("userName")String userName);
}
