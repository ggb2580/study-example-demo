package com.hrbu.shirodemo.realm;

import com.hrbu.shirodemo.entity.User;
import com.hrbu.shirodemo.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    protected IUserService userService;

    /*
    * 权限认证
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        String userName = user.getUserName();

        //查询用户角色和权限
        List<String> roles = Arrays.asList("ADMIN","USER");
        List<String> permissions = Arrays.asList("/api/**","/api/user/**");

        //创建AuthorizationInfo对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //添加角色
        simpleAuthorizationInfo.addRoles(roles); //多个角色

        //添加权限
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;


    }

    /*
    * 获取用户名及密码
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        String username = userToken.getUsername();
        String password = new String(userToken.getPassword());

        //从数据库查询用户
        User user = userService.query().eq("user_name",username).one();

        //用户不存在
        if (user == null ){
            throw new UnknownAccountException("用户不存在");
        }

        if (user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定");
        }

        //返回认证信息（参数：用户对象，数据库密码，当前relam名称）
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
//                ByteSource.Util.bytes(user),
                getName()
        );

    }
}
