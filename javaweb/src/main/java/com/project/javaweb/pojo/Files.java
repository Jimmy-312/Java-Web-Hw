package com.project.javaweb.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class Files implements Serializable{
    private Integer id;
    private String name;
    private String type;
    private Integer ownerid;
    private Date createtime;
    private Date updatetime;
    private String ispublic;
    private Integer srcid;
}
