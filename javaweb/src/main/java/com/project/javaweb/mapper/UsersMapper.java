package com.project.javaweb.mapper;

import java.util.List;

import com.project.javaweb.pojo.Users;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    int insert(Users user);

    Users selectById(Integer id);

    List<Users> selectAll();
}
