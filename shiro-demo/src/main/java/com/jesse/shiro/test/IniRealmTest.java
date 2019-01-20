package com.jesse.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author jesse
 * @date 2019/1/20 10:46
 * shiro 内置的Realm
 */
public class IniRealmTest {

    /**
     *IniRealm测试
     */
    @Test
    public void testIniRealm(){
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1、得到SecurityManager实例，并绑定给SecurityUtils
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //2、得到Subject及创建用户名和密码身份验证token（即用户身份/凭证）
        AuthenticationToken token = new UsernamePasswordToken("jesse","123") ;
        Subject subject = SecurityUtils.getSubject();

        try{
            //3、登录，即身份验证
            subject.login(token);
        }catch (AuthenticationException e){
            //4、身份验证失败
        }

        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");
        //subject.checkRole("admin1");
        subject.checkPermission("user:select");

        //退出
        subject.logout();

        System.out.println("退出后再验证："+subject.isAuthenticated());


    }

}
