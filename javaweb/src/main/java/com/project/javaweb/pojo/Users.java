package com.project.javaweb.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class Users implements Serializable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user")
    private String userid;
    private String name;
    private String gender;
    private String passwd;
    private String level;

    // @Override
    // public String toString(){
    //     return "{id:" + id + ",name:" + name + ",gender:"+ gender + ",level:" + level +"}";
    // }
}
