package com.qbb.cxda.cmm.controller;

import com.github.pagehelper.PageInfo;
import com.qbb.cxda.base.PageInfoBean;
import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.response.UserListResponse;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.config.PropertiesConfig;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    PropertiesConfig propertiesConfig;

    /**
     * 用户插入
     * @param user
     * @return
     */
    @PostMapping("insertUser")
    public ResultObject insertUser(@RequestBody User user){
        try{
            userService.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    @PostMapping("updateUser")
    public ResultObject updateUser(@RequestBody User user){
        if(user == null || CommonUtil.ifEmpty(user.getId())){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错");
        }
        try{
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("deleteUser")
    public ResultObject deleteUser(Integer userId){
        if(userId == null || userId == 0){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数有误");
        }
        try{
            userService.deleteUser(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"删除失败：未知错误");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    /**
     * 文档上传
     * @param file
     * @return
     */
    @PostMapping("uploadDoc")
    public String uploadDoc(File file){
        String filePath="123";
        return filePath;
    }

    /**
     * 分页查询用户在线用户列表
     * @param
     * @return
     */
    @PostMapping("pageListUsr")
    public ResultObject pageListUser(String name,Byte checkStatus,Integer page,Integer pageSize){
        if(page ==  null || pageSize == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数有误");
        }
        User user = new User(name,checkStatus);
        try{
            PageInfo<User> pageInfo = userService.SelectPageList(page,pageSize,user);
            PageInfoBean<UserListResponse> pageInfoBean = warpUserPage(pageInfo);
            return new ResultObject(BaseEnums.SUCCESS,pageInfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }
    }
    //用户分页列表接口封装
    private  PageInfoBean<UserListResponse> warpUserPage(PageInfo pageInfo){
        List<UserListResponse> userResopnseList = new ArrayList<UserListResponse>();
        List<User> tempList = pageInfo.getList();
        for (User user : tempList){
            UserListResponse userListResponse = new UserListResponse(user,propertiesConfig.getPrixPath());
            userResopnseList.add(userListResponse);
        }
        pageInfo.setList(userResopnseList);
        return new PageInfoBean<UserListResponse>(pageInfo);
    }

    /**
     * 注册用户
     * @param user  用户
     * @return
     */
    @PostMapping("register")
    public ResultObject Register(@RequestBody User user){
        try{
            userService.register(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

}
