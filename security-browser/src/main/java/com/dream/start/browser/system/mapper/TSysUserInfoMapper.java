package com.dream.start.browser.system.mapper;

import com.dream.start.browser.core.mapper.MyBatisBaseDao;
import com.dream.start.browser.system.pojo.TSysUserInfo;
import com.dream.start.browser.system.pojo.TSysUserInfoExample;
import org.springframework.stereotype.Repository;

/**
 * TSysUserInfoMapper继承基类
 */
@Repository
public interface TSysUserInfoMapper extends MyBatisBaseDao<TSysUserInfo, String, TSysUserInfoExample> {
}
