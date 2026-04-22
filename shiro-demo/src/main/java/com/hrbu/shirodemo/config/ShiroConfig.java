package com.hrbu.shirodemo.config;

import com.hrbu.shirodemo.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建 ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // 设置登录页面
        shiroFilter.setLoginUrl("/login.html");
        // 设置登录成功后的跳转页面
        shiroFilter.setSuccessUrl("/index");
        // 设置未授权页面
        shiroFilter.setUnauthorizedUrl("/unauthorized.html");

        // 配置过滤规则
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        // 静态资源和登录相关 - 无需认证
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/login.html", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/img/**", "anon");
        filterChainMap.put("/favicon.ico", "anon");

        // 需要认证的接口
        filterChainMap.put("/user/**", "authc");
        filterChainMap.put("/admin/**", "authc");

        // 退出
        filterChainMap.put("/logout", "logout");

        // 其他所有请求都需要认证
        filterChainMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilter;
    }

    /**
     * 创建 SecurityManager
     */
    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     * 开启 Shiro 注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}