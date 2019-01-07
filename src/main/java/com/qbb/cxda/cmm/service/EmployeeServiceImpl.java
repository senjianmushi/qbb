package com.qbb.cxda.cmm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qbb.cxda.cmm.dao.EmployeeMapper;
import com.qbb.cxda.cmm.entity.Employee;
import com.qbb.cxda.constant.BasecConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public int insertEmployee(Employee employee) {
        employee.setCheckStatus(BasecConstant.CHECK_STATUS_UNCHECK);
        employee.setIsDelete(BasecConstant.IS_DELETE_NO);
        return employeeMapper.insertSelective(employee);
    }

    @Override
    public int deleteEmployee(int id) {
        Employee employee = new Employee(id);
        employee.setIsDelete((byte) 1);
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public PageInfo<Employee> SelectPageList(Employee employee, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Employee> employeeList = employeeMapper.selectEmployee(employee);
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return pageInfo;
    }

    @Override
    public Employee selectObject(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }
}
