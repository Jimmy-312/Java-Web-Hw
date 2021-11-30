package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.TagFile;

public interface TagFileService {
    void insert(TagFile tagFile);
    TagFile selectById(int id);
    void deleteById(int id);
    List<TagFile> selectAll();
}
