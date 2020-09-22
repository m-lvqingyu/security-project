package com.dream.start.browser.system.service;

import com.dream.start.browser.core.enums.UserInfoDelFlag;
import com.dream.start.browser.core.enums.UserInfoStatus;
import com.dream.start.browser.system.mapper.TSysUserInfoMapper;
import com.dream.start.browser.system.pojo.TSysUserInfo;
import com.dream.start.browser.system.pojo.TSysUserInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/22 10:23
 */

@Service
public class TSysUserInfoService {

    @Autowired
    private TSysUserInfoMapper tSysUserInfoMapper;

    public TSysUserInfo findUserInfoByName(String userName){
        TSysUserInfoExample example = new TSysUserInfoExample();
        example.createCriteria()
                .andUsernameNotEqualTo(userName)
                .andStatusEqualTo(UserInfoStatus.EFFECTIVE.getIndex())
                .andDelFlagEqualTo(UserInfoDelFlag.EFFECTIVE.getIndex());
        List<TSysUserInfo> infoList = tSysUserInfoMapper.selectByExample(example);
        if(infoList == null || !infoList.isEmpty()){
            return null;
        }
        return infoList.get(0);
    }

    public TSysUserInfo findUserInfoByPhone(String phone){
        TSysUserInfoExample example = new TSysUserInfoExample();
        example.createCriteria()
                .andPhoneEqualTo(phone)
                .andStatusEqualTo(UserInfoStatus.EFFECTIVE.getIndex())
                .andDelFlagEqualTo(UserInfoDelFlag.EFFECTIVE.getIndex());
        List<TSysUserInfo> infoList = tSysUserInfoMapper.selectByExample(example);
        if(infoList == null || infoList.isEmpty()){
            return null;
        }
        return infoList.get(0);
    }

}
