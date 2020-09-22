package com.dream.start.browser.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * t_sys_user_info
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSysUserInfo {
    /**
     * 主键id
     */
    private String id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 状态(1-正常,2-冻结)
     */
    private Integer status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    private Integer delFlag;

    /**
     * 第三方登录的唯一标识
     */
    private String thirdId;

    /**
     * 第三方类型
     */
    private String thirdType;

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    private Integer activitiSync;

    /**
     * 工号，唯一键
     */
    private String workNo;

    /**
     * 身份（1普通成员 2上级）
     */
    private Integer userIdentity;

    /**
     * 职务，关联职务表
     */
    private String post;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

}
