package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.entity.Role;

public interface MapUserRoleService {

    /**
     * 通过userId查询用户的权限
     * @param userId
     * @return
     */
    Role findRoleByUserId(Integer userId);
}
