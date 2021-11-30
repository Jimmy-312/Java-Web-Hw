package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.FileSrcMapper;
import com.project.javaweb.pojo.FileSrc;
import com.project.javaweb.service.FileSrcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSrcServiceImpl implements FileSrcService{
    @Autowired
    private FileSrcMapper fileSrcMapper;

    public void insert(FileSrc fileSrc){
        fileSrcMapper.insert(fileSrc);
    }

    public FileSrc selectFileSrcById(int id){
        return fileSrcMapper.selectById(id);
    }

    public void deleteById(int id){
        fileSrcMapper.deleteById(id);
    }

    public List<FileSrc> selectAllFileSrc(){
        return fileSrcMapper.selectList(null);
    }
}
