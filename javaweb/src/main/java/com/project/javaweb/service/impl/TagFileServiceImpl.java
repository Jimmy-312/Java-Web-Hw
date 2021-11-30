package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.TagFileMapper;
import com.project.javaweb.pojo.TagFile;
import com.project.javaweb.service.TagFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagFileServiceImpl implements TagFileService {
    @Autowired
    private TagFileMapper mapper;

    public void insert(TagFile obj){
        mapper.insert(obj);
    }

    public TagFile selectById(int id){
        return mapper.selectById(id);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<TagFile> selectAll(){
        return mapper.selectList(null);
    }
}
