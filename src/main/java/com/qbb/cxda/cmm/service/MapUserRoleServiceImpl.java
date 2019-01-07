package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.dao.MapUserRoleMapper;
import com.qbb.cxda.cmm.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MapUserRoleServiceImpl implements MapUserRoleService {

    @Resource
    MapUserRoleMapper mapUserRoleMapper;

    @Override
    public Role findRoleByUserId(Integer userId) {
        return null;
    }
}
