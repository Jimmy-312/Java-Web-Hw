package com.project.javaweb.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.javaweb.mapper.FileSrcMapper;
import com.project.javaweb.pojo.FileSrc;
import com.project.javaweb.service.FileSrcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSrcServiceImpl implements FileSrcService{
    @Autowired
    private FileSrcMapper mapper;

    public void insert(FileSrc obj){
        mapper.insert(obj);
    }

    public FileSrc selectById(int id){
        return mapper.selectById(id);
    }

    public FileSrc selectByFileId(int fileid){
        QueryWrapper<FileSrc> wrapper = new QueryWrapper<>();
        wrapper.eq("fileid", fileid);
        return mapper.selectOne(wrapper);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public void update(FileSrc obj){
        mapper.updateById(obj);
    }

    public List<FileSrc> selectAll(){
        return mapper.selectList(null);
    }
}
