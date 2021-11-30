package com.project.javaweb.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class FileSrc implements Serializable{
    @TableId(type = IdType.AUTO)
    public Integer id;
    public String src;
}
