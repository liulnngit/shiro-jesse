package com.jesse.shiro.web.bind.annotation;


import com.jesse.shiro.utils.Constant;

import java.lang.annotation.*;

/**
 * @author jesse
 * @date 2019/1/20 13:18
 * /**
 * <p>绑定当前登录的用户</p>
 * <p>不同于@ModelAttribute</p>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default Constant.CURRENT_USER;
}
