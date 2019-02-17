package com.qbb.cxda.cmm.controller;

import com.github.pagehelper.PageInfo;
import com.qbb.cxda.base.PageInfoBean;
import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.cmm.entity.MapUserRole;
import com.qbb.cxda.cmm.entity.Role;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.response.UserListResponse;
import com.qbb.cxda.cmm.service.MapUserRoleService;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.config.PropertiesConfig;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import com.qbb.cxda.util.DateUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    private MapUserRoleService mapUserRoleService;

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
    @Transient
    public ResultObject updateUser(@RequestBody User user){
        if(user == null || CommonUtil.ifEmpty(user.getId())){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错");
        }
        try{
            userService.updateUser(user);
            // 审核通过
            if(user.getCheckStatus().equals((byte) 1)){
                MapUserRole mapUserRole = new MapUserRole();
                mapUserRole.setRoleId(Role.ROLE_MEMBER);//普通用户
                mapUserRole.setUserId(user.getId());
                mapUserRoleService.insertObject(mapUserRole);
            }
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

//    /**
//     * 文档上传
//     * @param file
//     * @return
//     */
//    @PostMapping("uploadDoc")
//    public String uploadDoc(File file){
//        String filePath="123";
//        return filePath;
//    }

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
            ResultObject validateResult = validation(user);
            if(validateResult.getCode() != 0){
                return validateResult;
            }
            String password = user.getPassword();
            // 生成6位数的随机数
            String salt = String.valueOf(new Random().nextInt(999999));
            user.setSalt(salt);
            Md5Hash md5Hash = new Md5Hash(password,salt);
            String passwordMd5 = md5Hash.toString();
            user.setPassword(passwordMd5);
            userService.register(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    ResultObject validation(User user){
        List<User> tempUserList = userService.selectUserList(new User(user.getUsername()));
        if(tempUserList.size() > 0 ){
            return new ResultObject(BaseEnums.EXITED_USER,"该用户名已注册");
        }
        User userTemp = new User();
        userTemp.setEmail(user.getEmail());
        tempUserList = userService.selectUserList(userTemp);
        if(tempUserList.size() > 0 ){
            return new ResultObject(BaseEnums.EXITED_EMAIL,"该邮箱已注册");
        }
        userTemp.setEmail(null);
        userTemp.setTelephone(user.getTelephone());
        tempUserList = userService.selectUserList(userTemp);
        if(tempUserList.size() > 0 ){
            return new ResultObject(BaseEnums.EXITED_TELEPHONE,"该手机号已注册");
        }
        userTemp.setTelephone(null);
        userTemp.setName(user.getName());
        tempUserList = userService.selectUserList(userTemp);
        if(tempUserList.size() > 0 ){
            return new ResultObject(BaseEnums.EXITED_TELEPHONE,"该公司名称已被注册");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    /**
     * 公司信息文档
     * @param mf
     * @return
     */
    @PostMapping("uploadDoc")
    public ResultObject uploadPic( @RequestParam(value="file") MultipartFile mf){
        if(mf == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"上传的文件有误");
        }
        //获取源文件
        String filename = mf.getOriginalFilename();
        String[] names=filename.split("\\.");
        String tempNum=(int)(Math.random()*100000)+"";
        Date now = new Date();
        //文件以年/月/文件名形式保存：/2018/12/xxxxx.jpg
        String dir = propertiesConfig.uploadNewsPath + "userdoc/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_MONTH)+"/";
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String uploadFileName =    "userdoc/" + DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_MONTH)+"/"
                +DateUtil.format(now,DateUtil.Pattern.NONE_DATETIME_SSS)+"."+names[names.length-1];
        File targetFile = new File (propertiesConfig.uploadNewsPath,uploadFileName);//目标文件

        //开始从源文件拷贝到目标文件
        //传图片一步到位
        try {
            mf.transferTo(targetFile);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = propertiesConfig.prixPath + uploadFileName;
        return new ResultObject(BaseEnums.SUCCESS,uploadFileName);
    }


    /**
     * 修改密码
     * @param userId    用户id
     * @param password  老密码
     * @param newPassword   新密码
     * @return
     */
    @PostMapping("updateUserPwd")
    @Transient
    public ResultObject updateUserPwd(String userId, String password,String newPassword){
        if(userId == null || CommonUtil.ifEmpty(userId)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错：用户id");
        }
        if(password == null || CommonUtil.ifEmpty(password)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错：老密码");
        }
        if(newPassword == null || CommonUtil.ifEmpty(newPassword)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错：新密码");
        }
        try{
            User user = userService.selectObject(Integer.valueOf(userId));
            byte result = userService.updatePwd(user,password,newPassword);
            if(result == 0){
                return new ResultObject(BaseEnums.WRONG_PASSWORD,"原始密码输入有误");
            }else if(result == 1){
                return new ResultObject(BaseEnums.SUCCESS);
            }else{
                return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"未知错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"操作失败");
        }

    }
}
