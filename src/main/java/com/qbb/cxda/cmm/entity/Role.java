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
}