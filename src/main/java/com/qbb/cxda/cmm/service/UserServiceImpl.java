package com.qbb.cxda.cmm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qbb.cxda.cmm.dao.UserMapper;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.constant.BasecConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(int userId) {
        User user = new User(userId);
        user.setIsDelete(BasecConstant.IS_DELETE_YES);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageInfo<User> SelectPageList(Integer page, Integer pageSize, User user) throws Exception {
        PageHelper.startPage(page,pageSize);
        List<User> userList = userMapper.selectUserList(user);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public int register(User user) {
        user.setCheckStatus(BasecConstant.CHECK_STATUS_UNCHECK);
        user.setType(BasecConstant.TYPE_NOMAL);
        user.setIsDelete(BasecConstant.IS_DELETE_NO);
        return userMapper.insertSelective(user);
    }

    @Override
    public User selectObject(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User selectUser(User user) {
        return userMapper.selectUserObject(user);
    }
}
