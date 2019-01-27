package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.dao.MapUserRoleMapper;
import com.qbb.cxda.cmm.entity.MapUserRole;
import com.qbb.cxda.cmm.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MapUserRoleServiceImpl implements MapUserRoleService {

    @Resource
    MapUserRoleMapper mapUserRoleMapper;

    @Override
    public MapUserRole findRoleByUserId(Integer userId) {
        return mapUserRoleMapper.selectObjectByUserId(userId);
    }

    @Override
    public int insertObject(MapUserRole mapUserRole) {
        return mapUserRoleMapper.insertSelective(mapUserRole);
    }
}
