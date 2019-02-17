package com.qbb.cxda.base;


import com.qbb.cxda.cmm.entity.MapUserRole;
import com.qbb.cxda.cmm.entity.Role;
import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.service.MapUserRoleService;
import com.qbb.cxda.cmm.service.RoleService;
import com.qbb.cxda.cmm.service.UserService;
import com.qbb.cxda.util.CommonUtil;
import com.qbb.cxda.util.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Set;

/**
 * 自定义realm
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    MapUserRoleService mapUserRoleService;

    @Autowired
    RoleService roleService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Subject subject=SecurityUtils.getSubject();
        User user= null;
        //String userName = (String) SerializeUtil.deserialize(subject.getPrincipal());
        try {
            byte[] bytes = (byte[]) subject.getPrincipal();
            user=  (User) SerializeUtil.deserialize(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //从数据库或者缓存中获取角色数据
        //Set<String> roles = null;
        //Set<String> permissions = null;
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(user.getUsername().equals("admin")){
            simpleAuthorizationInfo.addRole("admin");
            //simpleAuthorizationInfo.addStringPermission("user:all");
        }else{
            //User user = userService.selectUser(new User(userName));
            MapUserRole mapUserRole = mapUserRoleService.findRoleByUserId(user.getId());
            if(mapUserRole != null){
                Role role = roleService.selectObject(mapUserRole.getRoleId());
                simpleAuthorizationInfo.addRole(role.getRoleName());
            }else{
                simpleAuthorizationInfo.addRole("guest");
            }
            //simpleAuthorizationInfo.addStringPermission("user:no");
        }
        logger.info("---- 获取到以下角色 ----");
        logger.info(simpleAuthorizationInfo.getRoles().toString());
        //simpleAuthorizationInfo.addStringPermission(user.getAuthority());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证【登录】
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // TODO Auto-generated method stub
        logger.info("----------------  执行 Shiro 认证登录  ---------------------");
        //1：从主体春过来的认证信息获取用户信息
        UsernamePasswordToken authenUser = (UsernamePasswordToken) authenticationToken;
        String userName = authenUser.getUsername();
        String password = String.copyValueOf(authenUser.getPassword());
        //2：通过用户名到数据库中获取凭证
        User realUser = new User(userName,password);
        User newUser = userService.selectUser(realUser);
        if(newUser == null){//shiro会抛出UnknownAccountException异常
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(SerializeUtil.serialize(newUser),password,"userRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(newUser.getSalt()));//加盐
        return simpleAuthenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }


    public static void main(String args[]){
        String password = "1234567";
        String salt = "qbb";
        Md5Hash md5Hash = new Md5Hash(password,salt);
        System.out.println(md5Hash.toString());

    }
}
