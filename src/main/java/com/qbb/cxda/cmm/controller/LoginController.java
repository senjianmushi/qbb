package com.qbb.cxda.cmm.controller;


import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("dologin")
    @ResponseBody
    public ResultObject toLogin(String username, String password){
        if(CommonUtil.ifEmpty(username)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"用户名为空");
        }
        if(CommonUtil.ifEmpty(password)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"密码为空");
        }
        try{
            User tempuser = new User(username);
            User user = userService.selectUser(tempuser);
            Subject subject= SecurityUtils.getSubject();
            UsernamePasswordToken userToken = new UsernamePasswordToken(username, CommonUtil.encryptionMD5(password,user.getSalt()));
            subject.login(userToken);
//            HttpSession session = request.getSession();
//            session.setMaxInactiveInterval(1000 * 60 * 60);
//            session.setAttribute(session.getId(), subject);
            return new ResultObject(BaseEnums.SUCCESS,"登录成功");
        }catch (UnknownAccountException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"没有该用户");
        }catch (IncorrectCredentialsException e){
            return new ResultObject(BaseEnums.NO_SUCH_USER,"用户名密码错误");
        }
    }

    @GetMapping("/loginout")
    public String loginout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //session.removeAttribute("user");
        return "login";
    }
}
