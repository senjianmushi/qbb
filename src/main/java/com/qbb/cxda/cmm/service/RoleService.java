package com.qbb.cxda.cmm.service;

import com.qbb.cxda.cmm.entity.Role;

/**
 * @author lhj
 * @Description:
 * @Date: 2019/1/27 17:25
 */
public interface RoleService {

    /**
     * 查找单个role信息
     * @param roleId
     * @return
     */
    Role selectObject(Integer roleId);
}
