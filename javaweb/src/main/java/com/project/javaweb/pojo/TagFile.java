package com.project.javaweb.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class TagFile implements Serializable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer fileid;
}
