package com.project.javaweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.project.javaweb.mapper.FilesMapper;
import com.project.javaweb.pojo.FileSrc;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.TagFile;
import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.service.TagFileService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private TagFileService tagFileService;
    @Autowired
    private FileSrcService fileSrcService;

    @RequestMapping("/home")
    public String getMyFiles(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        List<Files> filesList = filesService.selectByOwner(user.getId());

        

        model.addAttribute("user", user);
        model.addAttribute("filelist", filesList);
        model.addAttribute("page", "home");

        return "files";
    }

    @RequestMapping("/public")
    public String getPublicFiles(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        List<Files> filesList = filesService.selectByPublic("Public");
        model.addAttribute("user", user);
        model.addAttribute("filelist", filesList);
        model.addAttribute("page", "public");

        return "files";
    }

    @PostMapping("/addfile")
    public String addNewFile(HttpSession session, @RequestParam Map<String, String> content, Model model) {
        Files file = new Files();
        TagFile tag = new TagFile();
        FileSrc src = new FileSrc();
        Users user = (Users) session.getAttribute("user");

        src.setSrc(content.get("name") + "." + content.get("type"));

        if (content.get("ispublic") != null) {
            file.setIspublic("Public");
        } else {
            file.setIspublic("Private");
        }

        file.setOwnerid(user.getId());
        file.setName(content.get("name"));
        file.setType(content.get("type"));
        file.setCreatetime(new Date());
        file.setUpdatetime(new Date());
        filesService.insert(file);

        src.setFileid(file.getId());
        fileSrcService.insert(src);

        tag.setFileid(file.getId());

        for (Map.Entry<String, String> entry : content.entrySet()) {
            if (entry.getKey().contains("tag")) {
                tag.setName(entry.getValue());
                tagFileService.insert(tag);
                tag.setId(tag.getId() + 1);
            }
        }

        if (content.get("page").equals("home")) {
            model.addAttribute("filelist", filesService.selectByOwner(user.getId()));
        } else {
            model.addAttribute("filelist", filesService.selectByPublic("Public"));
        }

        return "files::file_table";
    }

    @PostMapping("/delfile")
    public String delFile(HttpSession session, Model model, @RequestParam Map<String, Integer> content) {
        Collection<Integer> idList = content.values();
        Users user = (Users) session.getAttribute("user");

        for (Object id : idList.toArray()) {
            filesService.deleteById(Integer.parseInt(id.toString()));
        }

        model.addAttribute("filelist", filesService.selectByOwner(user.getId()));
        return "files::file_table";
    }

    @PostMapping("/upload")
    public String upLoadFile(@RequestParam("file") MultipartFile fileContent,@RequestParam("ispublic") String isPublic, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        String[] fileInfo = fileContent.getOriginalFilename().split("\\.");
        String fileType = fileInfo[fileInfo.length - 1];
        String fileName = fileInfo[0];
        Files file = new Files();
        FileSrc src = new FileSrc();

        file.setName(fileName);
        file.setOwnerid(user.getId());
        file.setType(fileType);
        file.setCreatetime(new Date());
        file.setUpdatetime(new Date());
        file.setIspublic(isPublic);
        filesService.insert(file);

        src.setFileid(file.getId());
        fileSrcService.insert(src);
        System.out.println(src.getId() + "." + fileType);
        src.setSrc(src.getId() + "." + fileType);
        fileSrcService.update(src);

        if (filesService.uploadFile(src.getSrc(), fileContent)) {
            System.out.println(fileName);
        } else {

        }

        model.addAttribute("filelist", filesService.selectByOwner(user.getId()));
        return "files::file_table";
    }
}
