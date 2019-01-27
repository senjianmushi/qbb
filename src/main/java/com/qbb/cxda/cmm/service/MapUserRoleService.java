package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.entity.MapUserRole;


public interface MapUserRoleService {

    /**
     * 通过userId查询用户的权限
     * @param userId
     * @return
     */
    MapUserRole findRoleByUserId(Integer userId);

    int insertObject(MapUserRole mapUserRole);
}
