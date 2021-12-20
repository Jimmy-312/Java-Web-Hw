package com.project.javaweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {
    @PostMapping("/upload/img")
    public Map<String, Object> hello(@RequestParam("file[]") MultipartFile content) throws IllegalStateException, IOException{
        String filename = content.getOriginalFilename();
        String filepath = "/root/cloudfile/"+filename;
        File dest = new File(filepath);
        Map<String,Object> info = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        Map<String,String> succMap = new HashMap<>();

        succMap.put(filename, filepath);

        data.put("errFiles","");
        data.put("succMap", succMap);

        info.put("msg","ok");
        info.put("code", 0);
        info.put("data", data);

        content.transferTo(dest);
        return info;
    }
}
