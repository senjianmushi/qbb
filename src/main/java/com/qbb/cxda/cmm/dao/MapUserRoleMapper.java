package com.qbb.cxda.cmm.dao;

import com.qbb.cxda.cmm.entity.MapUserRole;

public interface MapUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MapUserRole record);

    int insertSelective(MapUserRole record);

    MapUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapUserRole record);

    int updateByPrimaryKey(MapUserRole record);

    MapUserRole selectObjectByUserId(Integer userId);
}