package com.qbb.cxda.cmm.controller;


import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.base.UserRealm;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.context.SpringContextUtils;
import sun.security.krb5.Realm;

import java.util.Collection;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("dologin")
    @ResponseBody
    public ResultObject toLogin(String username, String password){

        try{
            Subject subject= SecurityUtils.getSubject();
            //如果还未认证
            if(!subject.isAuthenticated()) {
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
//                Session session = subject.getSession();
//                session.setAttribute("user",userTemp);
            }
            if(subject.isAuthenticated()){
                return new ResultObject(BaseEnums.SUCCESS,"登录成功");
            }else
                return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"认证登陆失败");

        }catch (UnknownAccountException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"没有该用户");
        }catch (IncorrectCredentialsException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"用户名密码错误");
        }
    }

    @GetMapping("/loginout")
    public String loginout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
//        DefaultWebSecurityManager defaultWebSecurityManager= SpringCon.getBean("securityManager");
//        Collection<UserRealm> reals= defaultWebSecurityManager.getRealms();
//        UserRealm authorizingRealm= (UserRealm) reals.toArray()[0];
//        PrincipalCollection principalCollection= SecurityUtils.getSubject().getPrincipals();
//        authorizingRealm.getAuthorizationCache().remove(principalCollection);
        return "login";
    }
}
