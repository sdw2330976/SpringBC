package com.sdw.soft.demo.mapper;

import com.sdw.soft.demo.vo.User;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by shangyd on 2017/9/24.
 */
@Mapper
public interface UserMapper {


    @Select("select * from User where user_name = #{userName}")
    public User findByName(@Param("userName")String userName);


    @Insert("insert into User (user_name,age) values (#{userName},#{age})")
    void addUser(User user);

    @Select("select * from User")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "age", column = "age")
    })
    public List<User> getAll();

    @Update("update User set age=#{age} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from User where id=#{id}")
    void deleteUser(User user);
}
