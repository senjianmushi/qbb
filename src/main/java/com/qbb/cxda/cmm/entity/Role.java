package com.qbb.cxda.cmm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private Integer id;
    private String roleName;
    private String remark;
    private Integer creater;
    private Date createTime;
    private Integer updater;
    private Date updateTime;

    public static final Integer ROLE_ADMIN = 1;//管理员
    public static final Integer ROLE_MEMBER= 2;//普通用户
}