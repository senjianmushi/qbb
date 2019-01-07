package com.qbb.cxda.base;


import com.qbb.cxda.cmm.entity.User;
import com.qbb.cxda.cmm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义realm
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){

        Subject subject=SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        //从数据库或者缓存中获取角色数据
        Set<String> roles = null;
        Set<String> permissions = null;
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        if(user.getName().equals("admin")){
            simpleAuthorizationInfo.addStringPermission("user:all");
        }else{
            simpleAuthorizationInfo.addStringPermission("user:no");
        }
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
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,password,"userRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(newUser.getSalt()));//加盐
        return simpleAuthenticationInfo;
    }

    public static void main(String args[]){
        String password = "1234567";
        String salt = "qbb";
        Md5Hash md5Hash = new Md5Hash(password,salt);
        System.out.println(md5Hash.toString());

    }
}
