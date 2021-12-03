package com.project.javaweb.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.javaweb.mapper.TagFileMapper;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.TagFile;
import com.project.javaweb.service.TagFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagFileServiceImpl implements TagFileService {
    @Autowired
    private TagFileMapper mapper;

    public void insert(TagFile obj) {
        mapper.insert(obj);
    }

    public TagFile selectById(int id) {
        return mapper.selectById(id);
    }

    public List<TagFile> selectByFileId(Integer fileId) {
        QueryWrapper<TagFile> wrapper = new QueryWrapper<>();
        wrapper.eq("fileid", fileId);
        return mapper.selectList(wrapper);
    }

    public List<Integer> getFileIdListByName(String name) {
        QueryWrapper<TagFile> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        List<TagFile> tagList = mapper.selectList(wrapper);
        List<Integer> fileIdList = new ArrayList<Integer>();
        for (TagFile tag : tagList) {
            fileIdList.add(tag.getFileid());
        }
        return fileIdList;
    }

    public HashSet<String> getTagNameByFileList(List<Files> filesList) {

        HashSet<String> tags = new HashSet<String>();

        for (Files file : filesList) {
            QueryWrapper<TagFile> wrapper = new QueryWrapper<>();
            wrapper.eq("fileid", file.getId());
            List<TagFile> tagList = mapper.selectList(wrapper);
            for (TagFile tag : tagList) {
                tags.add(tag.getName());
            }

        }
        return tags;
    }

    public List<String> getTagNameByFileId(Integer fileId) {
        QueryWrapper<TagFile> wrapper = new QueryWrapper<>();
        List<String> tagsName = new ArrayList<String>();
        wrapper.eq("fileid", fileId);
        List<TagFile> tagList = mapper.selectList(wrapper);
        for (TagFile tag : tagList) {
            tagsName.add(tag.getName());
        }
        return tagsName;
    }

    public List<String> getAllTagName() {
        List<String> nameList = new ArrayList<String>();
        return nameList;
    }

    public void deleteById(int id) {
        mapper.deleteById(id);
    }

    public List<TagFile> selectAll() {
        return mapper.selectList(null);
    }

}
