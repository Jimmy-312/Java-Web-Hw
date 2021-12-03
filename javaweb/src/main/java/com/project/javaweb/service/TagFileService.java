package com.project.javaweb.service;

import java.util.HashSet;
import java.util.List;

import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.TagFile;

public interface TagFileService {
    void insert(TagFile tagFile);
    TagFile selectById(int id);
    void deleteById(int id);
    List<TagFile> selectAll();
    List<TagFile> selectByFileId(Integer fileId);
    List<String> getAllTagName();
    HashSet<String> getTagNameByFileList(List<Files> filesList);
    List<Integer> getFileIdListByName(String name);
    List<String> getTagNameByFileId(Integer fileId);
}
