package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.Files;

import org.springframework.web.multipart.MultipartFile;

public interface FilesService {
    void insert(Files file);
    Files selectById(int id);
    void deleteByIds(List<Integer> ids);
    List<Files> selectAll();
    Files selectByName(String name);
    List<Files> selectByOwner(int ownerid);
    List<Files> selectByPublic(String string);
    boolean uploadFile(String fileName, MultipartFile fileContent);
    void update(Files files);
    List<Files> selectByIds(List<Integer> idList);
}
