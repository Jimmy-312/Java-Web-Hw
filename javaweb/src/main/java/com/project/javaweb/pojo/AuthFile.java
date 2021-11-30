package com.project.javaweb.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AuthFile implements Serializable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;  
    private Integer fileid;
    private String level;
}
