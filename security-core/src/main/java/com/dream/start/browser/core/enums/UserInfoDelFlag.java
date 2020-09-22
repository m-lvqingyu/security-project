package com.dream.start.browser.core.enums;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/22 10:40
 */
public enum UserInfoDelFlag {

    EFFECTIVE("有效", 0),
    DELETE("冻结", 1);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    UserInfoDelFlag(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
