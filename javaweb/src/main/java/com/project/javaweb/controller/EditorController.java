package com.project.javaweb.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.project.javaweb.pojo.AuthFile;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.AuthFileService;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.service.UsersService;
import com.project.javaweb.util.FileRW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EditorController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileSrcService fileSrcService;
    @Autowired
    private AuthFileService authFileService;
    @Autowired
    private UsersService usersService;

    @GetMapping("/editor")
    public String editor() {
        return "editor";
    }

    @PostMapping("/editor/{fileid}")
    @ResponseBody
    public Map<String, Object> editorLoad(@PathVariable("fileid") Integer fileId,HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");
        Map<String, Object> info = new HashMap<>();
        AuthFile relation = authFileService.selectByFileId(fileId,user.getId());
        if(relation==null){
            return info;
        }
        String level = relation.getLevel();
        //if(level)
        

        String content;
        String fileName = fileSrcService.selectByFileId(fileId).getSrc();

        info.put("level", level);
        content=FileRW.readFile(fileName);
        info.put("content", content);
        //System.out.println(info);
        return info;
    }

    @GetMapping("/editor/{fileid}")
    public String editorIndex(HttpSession session,@PathVariable("fileid") Integer fileId,Model model) throws IOException {
        Users user = (Users) session.getAttribute("user");
        Files file = filesService.selectById(fileId);
        Users owner = usersService.selectById(file.getOwnerid());
        String level = authFileService.selectByFileId(fileId, user.getId()).getLevel();
        String badge = "badge-";

        //System.out.println(level);

        if(level.equals("Super")){
            badge += "danger";
        }else
        if(level.equals("Viewer")){
            badge += "warning";
        }else
        if(level.equals("Editor")){
            badge += "success";
        }else{
            badge += "secondary";
        }

        model.addAttribute("user", user);
        model.addAttribute("fileid", fileId);
        model.addAttribute("file", file);
        model.addAttribute("owner", owner);
        model.addAttribute("badge", badge);
        model.addAttribute("level", level);

        return "editor";
    }

    @PostMapping("/save/{fileid}")
    @ResponseBody
    public String editorSave(@PathVariable("fileid") Integer fileId,@RequestParam("content") String content) throws IOException {
        Files file = filesService.selectById(fileId);
        String fileName = fileSrcService.selectByFileId(fileId).getSrc();

        FileRW.writeFile(fileName,content);
        file.setUpdatetime(new Date());
        filesService.update(file);

        
        return content;
    }
}
