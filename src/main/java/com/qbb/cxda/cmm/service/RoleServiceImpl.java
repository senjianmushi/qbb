package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.dao.RoleMapper;
import com.qbb.cxda.cmm.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lhj
 * @Description:
 * @Date: 2019/1/27 17:27
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    RoleMapper roleMapper;

    @Override
    public Role selectObject(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

}
