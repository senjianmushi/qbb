package com.qbb.cxda.web;

import com.qbb.cxda.cmm.entity.Employee;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.response.EmployeeListResponnse;
import com.qbb.cxda.cmm.response.UserListResponse;
import com.qbb.cxda.cmm.service.EmployeeService;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.config.PropertiesConfig;
import com.qbb.cxda.util.JedisUtil;
import com.qbb.cxda.util.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;
    @Autowired
    PropertiesConfig propertiesConfig;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String viewIndex(){
        return "index";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public String viewLogin(){
        return "login";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/loginout")
    public String viewLoginout(){
        return "loginout";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register(){return "register";}


    /**
     * 搜索页面
     * @return
     */
//    @RequiresRoles("admin,member")
    @RequestMapping("/adminIndex")
    public ModelAndView viewAdminIndex() throws IOException, ClassNotFoundException {

        Subject subject= SecurityUtils.getSubject();
        //Session session = subject.getSession();
        //System.out.println("subject.getPrincipal(): "+ subject.getPrincipal());
       // byte[] bytes = (byte[]) subject.getPrincipal();
        //User user=  (User) SerializeUtil.deserialize(bytes);
        System.out.println("登录成功跳转到后台首页：has.role ："+ subject.hasRole("admin"));
        ModelAndView modelAndView = new ModelAndView("admin/index");
        //session.setAttribute("user",user);
        return modelAndView;
    }

    /**
     * 黑名单界面
     * @return
     */
    @RequestMapping("/addProfile")
    public String viewAdminAddProfile(){return "admin/profile/addProfile";}

    /**
     * 放大图片
     * @param url
     * @return
     */
    @RequestMapping("/picDetail")
    public ModelAndView viewPicDetail(String url) {
        ModelAndView mv = new ModelAndView("admin/profile/picDetail");
        mv.addObject("url", url);
        return mv;
    }


    @RequestMapping("/profileManage")
    public String viewAdminProfileManage(){return "admin/check/employeeList";}

    /**
     * 候选人详细信息
     * @param employeeId
     * @param type
     * @return
     */
    @RequestMapping("/employeeDetail")
    public ModelAndView viewEmployeeDetail(Integer employeeId,String type) {
        ModelAndView mv = null;
        if("detail".equals(type)){
            mv = new ModelAndView("admin/check/employeeDetail");
        }else if("check".equals(type)){
            mv = new ModelAndView("admin/check/employeeCheck");
        }else if("update".equals(type)){
            mv = new ModelAndView("admin/check/employeeUpdate");
        }
        if(employeeId == null ){
            mv.addObject("employee", new Employee());
            return mv;
        }
        Employee employee = employeeService.selectObject(employeeId);
        if(employee == null){
            mv.addObject("employee",  new Employee());
            return mv;
        }
        EmployeeListResponnse employeeListResponse  = new EmployeeListResponnse(employee,propertiesConfig.getPrixPath());
        mv.addObject("employee", employeeListResponse);
        return mv;
    }

    /**
     * 管理员下的用户列表页面
     * @return
     */
    @RequestMapping("/userManage")
    public String viewAdminUserManage(){return "admin/check/userList";}

    /**
     * 用户申请人详细信息
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping("/userDetail")
    public ModelAndView viewUserDetail(Integer userId,String type) {
        ModelAndView mv = null;
        if("detail".equals(type)){
            mv = new ModelAndView("admin/check/userDetail");
        }else if("check".equals(type)){
            mv = new ModelAndView("admin/check/userCheck");
        }else if("update".equals(type)){
            mv = new ModelAndView("admin/check/userUpdate");
        }
        if(userId == null ){
            mv.addObject("employee", new Employee());
            return mv;
        }
        User user = userService.selectObject(userId);
        if(user == null){
            mv.addObject("user",  new User());
            return mv;
        }
        UserListResponse userListResponse  = new UserListResponse(user,propertiesConfig.getPrixPath());
        mv.addObject("user", userListResponse);
        return mv;
    }

    /**
     * 修改密码的页面
     * @return
     */
    @RequestMapping("updatePwd")
    public ModelAndView updatePwd(){
        ModelAndView mv = new ModelAndView("admin/user/updatePassword");
        return mv;
    }


}
