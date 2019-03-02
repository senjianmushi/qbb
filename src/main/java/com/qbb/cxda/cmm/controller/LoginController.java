package com.qbb.cxda.cmm.controller;


import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import com.qbb.cxda.util.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("dologin")
    @ResponseBody
    public ResultObject toLogin(String username, String password){

        try{
            Subject subject= SecurityUtils.getSubject();
            System.out.println("登录时：subject.isAuthenticated():"+subject.isAuthenticated());
            //subject.logout();
            //如果还未认证
            //if(!subject.isAuthenticated()) {
                if(CommonUtil.ifEmpty(username)){
                    return new ResultObject(BaseEnums.PARAM_ERROR,"用户名为空");
                }
                if(CommonUtil.ifEmpty(password)){
                    return new ResultObject(BaseEnums.PARAM_ERROR,"密码为空");
                }
                User tempuser = new User(username);
                User userTemp = userService.selectUser(tempuser);
                if(userTemp == null){
                    return new ResultObject(BaseEnums.PARAM_ERROR,"该用户未注册过");
                }
                UsernamePasswordToken userToken = new UsernamePasswordToken(username, CommonUtil.encryptionMD5(password,userTemp.getSalt()));
                subject.login(userToken);

//                session.setAttribute("user",userTemp);
            //}
            if(subject.isAuthenticated()){
//                System.out.println("subject.getPrincipal(): "+ subject.getPrincipal());
                byte[] bytes = (byte[]) subject.getPrincipal();
                Session session = subject.getSession();
                User user=  (User) SerializeUtil.deserialize(bytes);
                session.setAttribute("user",user);
                System.out.println("登录成功时1：has.role ："+ subject.hasRole("admin"));
                return new ResultObject(BaseEnums.SUCCESS,"登录成功");
            }else
                return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"认证登陆失败");

        }catch (UnknownAccountException | IOException | ClassNotFoundException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"用户名密码错误");
        }catch (IncorrectCredentialsException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"用户名密码错误");
        }
    }

    /**
     * 这里的登出还有问题，先让他跳转这个页面再通过过滤器返回消除信息
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @GetMapping("/loginout")
    public String loginout() throws IOException, ClassNotFoundException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
//            System.out.println("loginOUt执行完毕");
        }
        System.out.println("subject.isAuthenticated ："+ subject.isAuthenticated());
        return "redirect:/view/loginout";
    }
}
