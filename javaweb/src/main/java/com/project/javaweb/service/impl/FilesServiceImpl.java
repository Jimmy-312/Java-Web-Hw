package com.project.javaweb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.project.javaweb.mapper.FilesMapper;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesServiceImpl implements FilesService {
    @Autowired
    private FilesMapper mapper;

    public void insert(Files obj) {
        mapper.insert(obj);
    }

    public Files selectById(int id) {
        return mapper.selectById(id);
    }

    public Files selectByName(String name) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    public void update(Files files){
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("id", files.getId());
        mapper.update(files, wrapper);
    }

    public boolean uploadFile(String fileName, MultipartFile fileContent) {
        String filePath = "/cloudfiles/";
        File dest = new File(filePath + fileName);
        try {
            fileContent.transferTo(dest);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Files> selectByOwner(int ownerid) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("ownerid", ownerid);
        return mapper.selectList(wrapper);
    }

    public List<Files> selectByPublic(String ispublic) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("ispublic", ispublic);
        return mapper.selectList(wrapper);
    }

    public void deleteByIds(List<Integer> ids) {

        mapper.deleteBatchIds(ids);
    }

    public List<Files> selectAll() {
        return mapper.selectList(null);
    }

    public List<Files> selectByIds(List<Integer> idList){
        if(idList.isEmpty()){
            List<Files> list = new ArrayList<>();
            return list;
        }
        return mapper.selectBatchIds(idList);
    }
}
