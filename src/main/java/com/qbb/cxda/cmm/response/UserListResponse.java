package com.qbb.cxda.cmm.response;

import com.qbb.cxda.cmm.entity.User;
import lombok.Data;

@Data
public class UserListResponse {

    private int id;
    private String username;    //用户名
    private String name;        //公司名称
    private String email;        //邮箱
    private String telephone;     //电话
    private String docUrl;      //文档
    private Byte type;        //类型：0普通用户 1管理员
    private Byte checkStatus;

    public UserListResponse(){}

    public UserListResponse(User user,String prixPath){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.docUrl = prixPath + user.getDocUrl();
        this.type = user.getType();
        this.checkStatus = user.getCheckStatus();
    }

}
