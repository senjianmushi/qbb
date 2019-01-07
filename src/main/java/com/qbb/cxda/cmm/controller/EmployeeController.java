package com.qbb.cxda.cmm.controller;

import com.github.pagehelper.PageInfo;
import com.qbb.cxda.base.PageInfoBean;
import com.qbb.cxda.base.ResultObject;
import com.qbb.cxda.cmm.entity.Employee;
import com.qbb.cxda.cmm.response.EmployeeListResponnse;
import com.qbb.cxda.cmm.service.EmployeeService;
import com.qbb.cxda.config.PropertiesConfig;
import com.qbb.cxda.constant.BaseEnums;
import com.qbb.cxda.util.CommonUtil;
import com.qbb.cxda.util.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    PropertiesConfig propertiesConfig;

    /**
     * 黑名单插入
     * @param employee  候选人信息
     * @return
     */
    @PostMapping("insertEmployee")
    public ResultObject insertEmployee(@RequestBody Employee employee){
        if(employee == null){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数为空");
        }
        try{
            employeeService.insertEmployee(employee);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"插入失败：未知错误");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    @PostMapping("uploadEmployeePic")
    public ResultObject uploadPic( @RequestParam(value="file")MultipartFile mf){
        if(mf == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"上传的文件有误");
        }
        //获取源文件
        String filename = mf.getOriginalFilename();
        String[] names=filename.split("\\.");
        String tempNum=(int)(Math.random()*100000)+"";
        Date now = new Date();
        //文件以年/月/文件名形式保存：/2018/12/xxxxx.jpg
        String dir = propertiesConfig.uploadNewsPath + "blacklist/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
                DateUtil.format(now,DateUtil.Pattern.NONE_MONTH)+"/";
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String uploadFileName =    "blacklist/" + DateUtil.format(now,DateUtil.Pattern.NONE_YEAR)+"/"+
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

    @PostMapping("updateEmployee")
    public ResultObject updataEmployee( @RequestBody Employee employee){
        if(employee == null || CommonUtil.ifEmpty(employee.getId())){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数出错");
        }
        try{
            employeeService.updateEmployee(employee);
        }catch (Exception e){
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"插入失败：未知错误");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    @PostMapping("deleteEmployee")
    public ResultObject deleteEmployee(Integer empployeeId){
        if(empployeeId == null || empployeeId == 0){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数有误");
        }
        try{
            employeeService.deleteEmployee(empployeeId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"删除失败：未知错误");
        }
        return new ResultObject(BaseEnums.SUCCESS);
    }

    /**
     * 分页查询黑名单列表(普通用户)
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("pageListEmployee")
    public ResultObject pageListEmployee(String name,String identityId,Integer page,Integer pageSize){
        if(page ==  null || pageSize == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数有误");
        }
        if(CommonUtil.ifEmpty(name)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"名字参数有误");
        }
        if(CommonUtil.ifEmpty(identityId)){
            return new ResultObject(BaseEnums.PARAM_ERROR,"身份参数有写");
        }
        Employee employee = new Employee(name,identityId);
        try {
            PageInfo<Employee> pageInfo = employeeService.SelectPageList(employee,page,pageSize);
            PageInfoBean<EmployeeListResponnse> pageInfoBean = warpUserPage(pageInfo);
            return new ResultObject(BaseEnums.SUCCESS ,pageInfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"未知错误");
        }
    }

    /**
     * 分页查询黑名单列表
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("pageListEmployeeAdmin")
    public ResultObject pageListEmployeeAdmin(String name,Byte checkStatus,Integer page,Integer pageSize){
        if(page ==  null || pageSize == null ){
            return new ResultObject(BaseEnums.PARAM_ERROR,"传入参数有误");
        }
        Employee employee = new Employee(name,checkStatus);
        try {
            PageInfo<Employee> pageInfo = employeeService.SelectPageList(employee,page,pageSize);
            int i = 0;
            PageInfoBean<EmployeeListResponnse> pageInfoBean = warpUserPage(pageInfo);
            return new ResultObject(BaseEnums.SUCCESS ,pageInfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObject(BaseEnums.UNEXPECTED_ERROR,"未知错误");
        }
    }

    public PageInfoBean<EmployeeListResponnse> warpUserPage(PageInfo pageInfo){
        List<EmployeeListResponnse> userResopnseList = new ArrayList<EmployeeListResponnse>();
        List<Employee> tempList = pageInfo.getList();
        for (Employee employee : tempList){
            EmployeeListResponnse employeeListResponse  = new EmployeeListResponnse(employee,propertiesConfig.getPrixPath());
            userResopnseList.add(employeeListResponse);
        }
        pageInfo.setList(userResopnseList);
        return new PageInfoBean<EmployeeListResponnse >(pageInfo);
    }
}
