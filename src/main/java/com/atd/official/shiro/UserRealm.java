package com.atd.official.shiro;

import com.atd.official.entity.User;
import com.atd.official.mapper.UserMapper;
import com.atd.official.tools.Constants;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    //权限管理
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1. 获取授权的用户
        Object principal = principalCollection.getPrimaryPrincipal();
        //2.下面使用Set<String> roles来构造SimpleAuthorizationInfo
       // SimpleAuthorizationInfo info = null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if ("atd".equals(principal)){
            info.addStringPermission("admin");
        }
        return info;
    }


    /**
     * 验证当前登录的Subject
     * LoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        User user = userMapper.findByUsername(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUser_login(), //用户名
                user.getUser_pass(), //密码
                ByteSource.Util.bytes(user.getUser_login()+ Constants.SALT),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;

    }
}
