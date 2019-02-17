package com.qbb.cxda.cmm.service;

import com.github.pagehelper.PageInfo;
import com.qbb.cxda.cmm.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 插入用户
     * @param user 用户信息
     * @return
     */
    int insertUser(User user);

    /**
     * 修改用户
     * @param user 用户信息
     * @return
     */
    int updateUser(User user);

    /**
     * 用户逻辑删除
     * @param userId 用户id
     * @return
     */
    int deleteUser(int userId);

    /**
     * 分页查询用户列表
     * @param page  当前页
     * @param pageSize  一页显示条数
     * @param user  用户
     * @return
     * @throws Exception
     */
    PageInfo<User> SelectPageList(Integer page, Integer pageSize, User user) throws Exception;

    /**
     * 用户注册
     * @param user  用户信息
     * @return
     */
    int register(User user);

    /**
     * 通过id去查找用户
     * @param userId
     * @return
     */
    User selectObject(Integer userId);

    /**
     * 查找用户
     * @param user
     * @return
     */
    User selectUser(User user);

    /**
     * 查找用户
     * @param user
     * @return
     */
    List<User> selectUserList(User user);

    /**
     * 修改密码
     * @param user  用户信息
     * @param oldPwd    原始密码
     * @param newPwd    新密码
     * @return  0：密码相同；1：修改成功；-1：修改失败
     */
    byte updatePwd(User user, String oldPwd, String newPwd);


}
