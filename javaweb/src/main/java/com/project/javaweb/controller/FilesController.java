package com.project.javaweb.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.text.AbstractDocument.Content;

import org.springframework.ui.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.javaweb.pojo.AuthFile;
import com.project.javaweb.pojo.FileSrc;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.pojo.TagFile;
import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.AuthFileService;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.service.TagFileService;
import com.project.javaweb.service.UsersService;
import com.project.javaweb.util.DesUtil;
import com.project.javaweb.util.FileRW;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private TagFileService tagFileService;
    @Autowired
    private FileSrcService fileSrcService;
    @Autowired
    private AuthFileService authFileService;
    @Autowired
    private UsersService usersService;

    private Integer pageNum = 1;

    @GetMapping("/{op}/")
    public String getMyFiles(@PathVariable("op") String page, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        List<Files> filesList;
        Page<Files> pageFiles;
        pageNum = 1;
        // System.out.println(JSON.toJSONString(filesService.selectByPage().getRecords()));

        if (!page.equals("home")) {
            pageFiles = filesService.selectByPublic("Public", pageNum);

        } else {
            pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);
        }
        filesList = pageFiles.getRecords();
        model.addAttribute("tags", tagFileService.getTagNameByFileList(filesList));
        model.addAttribute("user", user);
        model.addAttribute("filelist", filesList);
        model.addAttribute("page", page);
        model.addAttribute("pagesum", (pageFiles.getPages() == 0) ? 1 : pageFiles.getPages());
        model.addAttribute("currentpage", pageNum);
        return "files";
    }

    @PostMapping("/gettags")
    @ResponseBody
    public String getTagsByFileId(@RequestParam("fileid") Integer fileId) {
        List<String> tagList = tagFileService.getTagNameByFileId(fileId);

        return String.join(",", tagList);
    }

    @PostMapping("/editfile")
    public String editFile(@RequestParam Map<String, String> content, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        TagFile tag;
        Integer fileId = Integer.parseInt(content.get("id"));
        Files file = filesService.selectById(fileId);
        List<TagFile> tagList = tagFileService.selectByFileId(fileId);
        Integer num = 0;

        file.setName(content.get("name"));
        file.setUpdatetime(new Date());

        if (content.get("ispublic") != null) {
            file.setIspublic("Public");
        } else {
            file.setIspublic("Private");
        }

        filesService.update(file);

        for (Map.Entry<String, String> entry : content.entrySet()) {
            if (entry.getKey().contains("tag")) {
                if (num < tagList.size()) {
                    tag = tagList.get(num++);
                    tag.setName(entry.getValue());
                    tagFileService.update(tag);
                } else {
                    tag = new TagFile();
                    tag.setFileid(fileId);
                    tag.setName(entry.getValue());
                    tagFileService.insert(tag);
                }
            }
        }

        while (num < tagList.size()) {
            tagFileService.deleteById(tagList.get(num).getId());
            num++;
        }

        List<Files> filesList;
        Page<Files> pageFiles;
        if (content.get("page").equals("home")) {
            pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);
        } else {
            pageFiles = filesService.selectByPublic("Public", pageNum);
        }

        filesList = pageFiles.getRecords();
        model.addAttribute("filelist", filesList);
        model.addAttribute("pagesum", pageFiles.getPages());
        model.addAttribute("currentpage", pageNum);
        return "files::file_table";
    }

    @PostMapping("/addfile")
    public String addNewFile(HttpSession session, @RequestParam Map<String, String> content, Model model)
            throws UnsupportedEncodingException {
        Files file = new Files();
        TagFile tag = new TagFile();
        FileSrc src = new FileSrc();
        AuthFile authFile = new AuthFile();
        Users user = (Users) session.getAttribute("user");

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
        src.setSrc("" + file.getId() + "." + content.get("type"));
        fileSrcService.insert(src);

        authFile.setFileid(file.getId());
        authFile.setUserid(user.getId());
        authFile.setLevel("Super");
        authFileService.insert(authFile);

        FileRW.writeFile(src.getSrc(), "");

        tag.setFileid(file.getId());

        for (Map.Entry<String, String> entry : content.entrySet()) {
            if (entry.getKey().contains("tag")) {
                tag.setName(entry.getValue());
                tagFileService.insert(tag);
                tag.setId(tag.getId() + 1);
            }
        }

        List<Files> filesList;
        Page<Files> pageFiles;
        if (content.get("page").equals("home")) {
            pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);
        } else {
            pageFiles = filesService.selectByPublic("Public", pageNum);
        }

        filesList = pageFiles.getRecords();
        model.addAttribute("filelist", filesList);
        model.addAttribute("pagesum", pageFiles.getPages());
        model.addAttribute("currentpage", pageNum);
        return "files::file_table";
    }

    @PostMapping("/delfile")
    public String delFile(HttpSession session, Model model,
            @RequestParam Map<String, Integer> content) {
        List<Integer> idList = new ArrayList<Integer>(content.values());
        Users user = (Users) session.getAttribute("user");

        filesService.deleteByIds(idList);

        List<Files> filesList;
        Page<Files> pageFiles;

        pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);

        filesList = pageFiles.getRecords();
        model.addAttribute("filelist", filesList);
        model.addAttribute("pagesum", (pageFiles.getPages() == 0) ? 1 : pageFiles.getPages());

        model.addAttribute("currentpage", pageNum);
        return "files::file_table";
    }

    @PostMapping("/upload")
    public String upLoadFile(@RequestParam("file") MultipartFile fileContent, @RequestParam("ispublic") String isPublic,
            HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        String[] fileInfo = fileContent.getOriginalFilename().split("\\.");
        String fileType = fileInfo[fileInfo.length - 1];
        String fileName = fileInfo[0];
        Files file = new Files();
        FileSrc src = new FileSrc();
        AuthFile auth = new AuthFile();
        TagFile tag = new TagFile();

        file.setName(fileName);
        file.setOwnerid(user.getId());
        file.setType(fileType);
        file.setCreatetime(new Date());
        file.setUpdatetime(new Date());
        file.setIspublic(isPublic);
        filesService.insert(file);

        src.setFileid(file.getId());
        fileSrcService.insert(src);
        // System.out.println(src.getId() + "." + fileType);
        src.setSrc(src.getId() + "." + fileType);
        fileSrcService.update(src);

        auth.setFileid(file.getId());
        auth.setLevel("Super");
        auth.setUserid(user.getId());
        authFileService.insert(auth);

        tag.setFileid(file.getId());
        tag.setName("Upload");
        tagFileService.insert(tag);

        if (filesService.uploadFile(src.getSrc(), fileContent)) {
            System.out.println(fileName);
        } else {

        }

        List<Files> filesList;
        Page<Files> pageFiles;
        pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);
        filesList = pageFiles.getRecords();
        model.addAttribute("filelist", filesList);
        model.addAttribute("pagesum", pageFiles.getPages());
        model.addAttribute("currentpage", pageNum);
        return "files::file_table";
    }

    @PostMapping("/{op}/{tagname}")
    public String switchTag(@PathVariable("op") String page, @PathVariable("tagname") String tagName, Model model,
            HttpSession session, @RequestParam("oc") Integer oc) {
        Users user = (Users) session.getAttribute("user");
        if (oc.equals(0)) {
            pageNum = 1;
        }

        List<Integer> fileIdList = tagFileService.getFileIdListByName(tagName);
        if (fileIdList.size() == 0) {
            tagName = "All";
            // System.out.println(123);
        }

        if (tagName.equals("All")) {
            List<Files> filesList;
            Page<Files> pageFiles;
            Boolean flag = false;
            do {
                if (flag) {
                    pageNum -= 1;
                }
                if (page.equals("home")) {
                    pageFiles = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()), pageNum);
                } else {
                    pageFiles = filesService.selectByPublic("Public", pageNum);
                }
                if (pageFiles.getTotal() == 0) {
                    break;
                }

                flag = true;
            } while (pageNum > pageFiles.getPages());

            filesList = pageFiles.getRecords();
            model.addAttribute("pagesum", (pageFiles.getPages() == 0) ? 1 : pageFiles.getPages());
            model.addAttribute("filelist", filesList);
            model.addAttribute("currentpage", pageNum);
            // System.out.println(pageFiles.getPages());

            return "files::file_table";
        }

        List<Files> fileList;
        List<Files> newFileList = new ArrayList<Files>();

        Page<Files> pageFiles = new Page<>();
        if (page.equals("home")) {
            fileList = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()));
        } else {
            fileList = filesService.selectByPublic("Public");
        }

        for (Files file : fileList) {
            if (fileIdList.contains(file.getId())) {
                newFileList.add(file);
            }
        }

        pageFiles.setTotal(newFileList.size());
        pageFiles.setSize(8);
        if (pageNum > pageFiles.getPages()) {
            pageNum -= 1;
        }
        pageFiles.setRecords(newFileList.subList(8 * (pageNum - 1),
                ((pageNum) * 8 <= newFileList.size()) ? pageNum * 8 : newFileList.size()));
        pageFiles.setCurrent(pageNum);
        model.addAttribute("filelist", pageFiles.getRecords());
        model.addAttribute("pagesum", pageFiles.getPages());
        model.addAttribute("currentpage", pageNum);
        return "files::file_table";
    }

    @PostMapping("/{op}/refreshTaglist")
    public String refreshTagList(@PathVariable("op") String page, Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        List<Files> filesList;

        if (page.equals("home")) {
            filesList = filesService.selectByIds(authFileService.selectFileIdByUserId(user.getId()));
        } else {
            filesList = filesService.selectByPublic("Public");
        }
        // System.out.println(filesList);
        model.addAttribute("tags", tagFileService.getTagNameByFileList(filesList));
        model.addAttribute("page", page);
        return "files::tags_list";
    }

    @PostMapping("/getauth")
    @ResponseBody
    public String loadAuth(@RequestParam("fileid") Integer fileId, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        AuthFile relation = authFileService.selectByFileId(fileId, user.getId());
        if (relation == null) {
            return "None";
        }
        return relation.getLevel();
    }

    @PostMapping("/getauthuser")
    @ResponseBody
    public Map<String, Object> getFileAuthUsers(@RequestParam("fileid") Integer fileid) {
        Map<String, Object> info = new HashMap<>();
        List<AuthFile> authList = authFileService.selectByFileId(fileid);

        for (AuthFile auth : authList) {
            Users user = usersService.selectById(auth.getUserid());
            info.put(user.getName(), auth.getLevel());
        }

        return info;
    }

    @PostMapping("/changeauth")
    @ResponseBody
    public Map<String, Object> changeAuth(@RequestParam("fileid") Integer fileid,
            @RequestParam("username") String userName, @RequestParam("type") String authType, HttpSession session) {
        Map<String, Object> info = new HashMap<>();
        Users user = usersService.selectByName(userName);
        Users myUser = (Users) session.getAttribute("user");
        if (user == null || user.getId().equals(myUser.getId())) {
            info.put("error", "No such user or cant change auth for self");
            return info;
        }

        AuthFile authFile = authFileService.selectByFileId(fileid, user.getId());
        if (authFile == null) {
            authFile = new AuthFile();
            authFile.setFileid(fileid);
            authFile.setUserid(user.getId());
            authFile.setLevel(authType);
            authFileService.insert(authFile);
            info.put("ok", fileid);
        } else {
            authFile.setLevel(authType);
            authFileService.update(authFile);
            info.put("ok", fileid);
        }
        // System.out.println(userName+authType+fileid);

        return info;
    }

    @PostMapping("/delauth")
    @ResponseBody
    public Map<String, Object> delAuth(@RequestParam("fileid") Integer fileid,
            @RequestParam(value = "name", required = false) String userName, HttpSession session) {
        Map<String, Object> info = new HashMap<>();
        if (userName == null) {
            userName = ((Users) session.getAttribute("user")).getName();
        }
        Users user = usersService.selectByName(userName);

        authFileService.deleteByRelate(user.getId(), fileid);

        return info;
    }

    @PostMapping("/pageadd")
    @ResponseBody
    public String pageadd() {
        pageNum += 1;
        return "pageadd";
    }

    @PostMapping("/pageminus")
    @ResponseBody
    public String pageminus() {
        pageNum -= 1;
        pageNum = pageNum < 1 ? 1 : pageNum;
        return "pageminus";
    }

    @PostMapping("/changepage/{op}")
    @ResponseBody
    public String changepage(@PathVariable("op") Integer page) {
        pageNum = page;
        return "pagechange";
    }

    @PostMapping("/search")
    public String searchFile(@RequestParam("words") String words, @RequestParam("page") String page, Model model,
            HttpSession session) {
        // System.out.println(words);
        Users user = (Users) session.getAttribute("user");
        List<Files> filelist = new ArrayList<>();
        List<Files> newfilelist = new ArrayList<>();

        filelist = filesService.searchByName(words);

        for (Files file : filelist) {
            if (page.equals("home")) {
                if (authFileService.selectByFileId(file.getId(), user.getId())!=null) {
                    newfilelist.add(file);
                }
            } else {
                if (file.getIspublic().equals("Public")) {
                    newfilelist.add(file);
                }
            }

        }

        model.addAttribute("filelist", newfilelist);
        model.addAttribute("pagesum", 1);
        model.addAttribute("currentpage", 1);
        return "files::file_table";
    }

    @PostMapping("/share")
    @ResponseBody
    public String processShare(@RequestParam("fileid") String fileid) throws UnsupportedEncodingException {
        String url = "/share/";
        String content = DesUtil.DESEncrypt("Jimmy312", fileid);
        url += content;
        // System.out.println(url);

        return url;
    }

    @GetMapping("/share/{code}")
    public String share(@PathVariable("code") String code, HttpSession session, Model model)
            throws UnsupportedEncodingException {
        Users user = (Users) session.getAttribute("user");

        Integer fileid;
        String content = DesUtil.DESDecrypt("Jimmy312", code);
        if (content != null) {
            fileid = Integer.valueOf(content);
        } else {
            model.addAttribute("info", "err");
            return "share";
        }

        Files file = filesService.selectById(fileid);
        if (filesService.selectById(fileid) == null) {
            model.addAttribute("info", "err");
            return "share";
        }

        AuthFile authFile = authFileService.selectByFileId(fileid, user.getId());
        if (authFile == null) {
            authFile = new AuthFile();
            authFile.setFileid(fileid);
            authFile.setUserid(user.getId());
            authFile.setLevel("Viewer");
            authFileService.insert(authFile);
        } else {
            authFile.setLevel("Viewer");
            authFileService.update(authFile);
        }

        model.addAttribute("info", "ok");
        model.addAttribute("file", file);
        model.addAttribute("user", user);

        return "share";
    }
}
