package com.slm.config;


import com.slm.pojo.User;
import com.slm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权逻辑PrincipalCollection");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串
//        info.addStringPermission("user:add");

        //通过用户信息中的权限设置
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        info.addStringPermission(user.getPerms());

        //将登录信息添加的session
        subject.getSession().setAttribute("loginUser", user);

        return info;
    }

    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证逻辑AuthenticationToken");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());
        if (user == null) {
            return null;
        }

        if (!user.getPwd().equals(new String(userToken.getPassword()))) {
            return null;
        }


//        return new SimpleAuthenticationInfo("", userToken.getPassword(), "");
        return new SimpleAuthenticationInfo(user, userToken.getPassword(), "");

        //假设数据库的用户名和密码
//        String name = "root";
//        String password = "123456";

//        //用户名不存在
//        if (!userToken.getUsername().equals(name)) {
//            return null;//shiro底层就会抛出UnknownAccountException
//        }
//        //2.验证密码,我们可以使用一个AuthenticationInfo实现类SimpleAuthenticationInfo
//        //shiro会自动帮我们验证！重点是第二个参数就是要验证的密码！
//        return new SimpleAuthenticationInfo("", password, "");
    }

}
