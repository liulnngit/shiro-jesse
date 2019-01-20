package com.jesse.shiro.enums;

/**
 * @author jesse
 * @date 2019/1/20 13:57
 */
public enum ResourceType {
    menu("菜单"), button("按钮");

    private final String info;

    private ResourceType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
