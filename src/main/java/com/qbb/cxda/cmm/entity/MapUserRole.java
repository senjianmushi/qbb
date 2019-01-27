package com.qbb.cxda.cmm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MapUserRole {
    private Integer id;
    private Integer roleId;
    private Integer userId;
    private String remarks;
    private Integer creater;
    private Date createTime;
    private Integer updater;
    private Date updateTime;

    public MapUserRole(){};

    public MapUserRole(Integer userId){
        this.userId = userId;
    }
}