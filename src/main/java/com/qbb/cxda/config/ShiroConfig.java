package com.qbb.cxda.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.qbb.cxda.base.UserRealm;
import com.qbb.cxda.shrio.cache.RedisCachManger;
import com.qbb.cxda.shrio.session.CustomerSessionManager;
import com.qbb.cxda.shrio.session.RedisSessionDao;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * shrio的拦截配置
     * @param defaultWebSecurityManager
     * @return
     */
    @Autowired
    private RedisSessionDao redisSessionDao;

    /**
     * shrio的拦截配置
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //被拦截返回登录页面
        shiroFilterFactoryBean.setLoginUrl("/view/login");
        //授权拦截返回页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/view/login");
        //拦截页面
        Map<String,String> fMap = new LinkedHashMap<>();
        fMap.put("/","anon");
        fMap.put("/static/**","anon");
        fMap.put("/css/**","anon");
        fMap.put("/img/**","anon");
        fMap.put("/js/**","anon");
        fMap.put("/plugin/**","anon");
        fMap.put("/download/**","anon");
        fMap.put("/view/login","anon");
        fMap.put("/view/register","anon");
        fMap.put("/view/index","anon");
        fMap.put("/dologin","anon");
        fMap.put("/logout","logout");
        fMap.put("/user/register","anon");
        fMap.put("/user/uploadDoc","anon");
        fMap.put("/blacklist/**","anon");
        fMap.put("/userdoc/**","anon");
        fMap.put("/**","authc");
        //拦截未授权
        //fMap.put("/all","perms[user:all]");
        //fMap.put("/one","perms[user:one]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建SecurityManager对象
     * @param userRealm
     * @return
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(
            @Qualifier("userRealm")UserRealm userRealm,
            @Qualifier("customerSessionManager") CustomerSessionManager customerSessionManager,
            @Qualifier("redisCachManger") RedisCachManger redisCachManger){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //设置session的管理器
        defaultWebSecurityManager.setSessionManager(customerSessionManager);
        defaultWebSecurityManager.setCacheManager(redisCachManger);
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }
    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    @Bean(name = "customerSessionManager")
    public CustomerSessionManager sessionManager(){
        CustomerSessionManager shiroSessionManager = new CustomerSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        shiroSessionManager.setSessionDAO(redisSessionDao);
        return shiroSessionManager;
    }

    //定义redis缓存管理
    @Bean(name = "redisCachManger")
    public RedisCachManger redisCachManger(){
        return new RedisCachManger();
    }

    //这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
