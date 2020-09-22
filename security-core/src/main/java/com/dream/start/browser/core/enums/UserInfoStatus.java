package com.dream.start.browser.core.enums;
/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/22 10:35
 */

public enum UserInfoStatus {

    EFFECTIVE("有效", 1),
    FREEZE("冻结", 2);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    UserInfoStatus(String name, int index) {
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
