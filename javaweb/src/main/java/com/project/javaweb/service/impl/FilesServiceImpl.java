package com.project.javaweb.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.javaweb.mapper.FilesMapper;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl implements FilesService{
    @Autowired
    private FilesMapper mapper;

    public void insert(Files obj){
        mapper.insert(obj);
    }

    public Files selectById(int id){
        return mapper.selectById(id);
    }

    public Files selectByName(String name){
        QueryWrapper<Files> wrapper =new QueryWrapper<>();
        wrapper.eq("name",name);
        return mapper.selectOne(wrapper);
    }
    
    public List<Files> selectByOwner(int ownerid){
        QueryWrapper<Files> wrapper =new QueryWrapper<>();
        wrapper.eq("ownerid",ownerid);
        return mapper.selectList(wrapper);
    }

    public List<Files> selectByPublic(String ispublic){
        QueryWrapper<Files> wrapper =new QueryWrapper<>();
        wrapper.eq("ispublic",ispublic);
        return mapper.selectList(wrapper);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<Files> selectAll(){
        return mapper.selectList(null);
    }
}
