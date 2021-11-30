package com.project.javaweb.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.project.javaweb.pojo.FileSrc;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.TagFile;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.service.TagFileService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FilesController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private TagFileService tagFileService;
    // @Autowired
    // private FileSrcService fileSrcService;

    @RequestMapping("/home")
    public String getAllFiles(Model model){
        List<Files> filesList = filesService.selectAll();
        model.addAttribute("filelist",filesList);

        return "files";
    }

    @PostMapping("/addfile")
    public String addNewFile(@RequestParam Map<String,String> content,Model model){
        Files file = new Files();
        TagFile tag = new TagFile();
        FileSrc src = new FileSrc();
        Date now = new Date();


        file.setName(content.get("name"));
        file.setIspublic(content.get("ispublic"));
        file.setType(content.get("type"));
        file.setCreatetime(now);
        file.setUpdatetime(now);
        
        file = filesService.selectById(filesService.insert(file));
        System.out.println(file);

        tag.setFileid(file.getId());

        
        for (Map.Entry<String,String> entry : content.entrySet()) {  
            if(entry.getKey().contains("tag")){
                tag.setName(entry.getValue());
                tagFileService.insert(tag);
            }
        }
        

        model.addAttribute("filelist",filesService.selectAll());
        return "files::file_table";
    }

}
