package com.qbb.cxda.cmm.dao;

import com.qbb.cxda.cmm.entity.Checklist;

public interface ChecklistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Checklist record);

    int insertSelective(Checklist record);

    Checklist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Checklist record);

    int updateByPrimaryKey(Checklist record);
}