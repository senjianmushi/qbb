package com.qbb.cxda.cmm.service;

import com.github.pagehelper.PageInfo;
import com.qbb.cxda.cmm.entity.Employee;

public interface EmployeeService {

    /**
     * 增加很名单
     * @param employee
     * @return
     */
    int insertEmployee(Employee employee);

    /**
     * 删除黑名单
     * @param id
     * @return
     */
    int deleteEmployee(int id);

    /**
     * 修改黑名单
     * @param employee
     */
    void updateEmployee(Employee employee);

    PageInfo<Employee> SelectPageList(Employee employee,int page,int pageSize);

    Employee selectObject(Integer id);
}
