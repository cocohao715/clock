package com.gduf.clock.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.gduf.clock.core.UserInfoDecode;
import com.gduf.clock.dao.DailyRecordMapper;
import com.gduf.clock.dao.UserClockInfoMapper;
import com.gduf.clock.dao.UserInfoMapper;
import com.gduf.clock.entity.DailyRecord;
import com.gduf.clock.entity.UserClockInfo;
import com.gduf.clock.entity.UserInfo;
import com.gduf.clock.exception.UserException;
import com.gduf.clock.service.UserService;
import com.gduf.clock.util.DateUtils;
import com.gduf.clock.vo.DetailVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserInfoMapper userInfoMapper;
    private UserClockInfoMapper userClockInfoMapper;
    private DailyRecordMapper dailyRecordMapper;
    public UserServiceImpl(UserInfoMapper userInfoMapper,UserClockInfoMapper userClockInfoMapper,
                           DailyRecordMapper dailyRecordMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userClockInfoMapper=userClockInfoMapper;
        this.dailyRecordMapper=dailyRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo userLogin(String encryptedData, String iv, String code) {
        //解密
        Map map = UserInfoDecode.decode(encryptedData, iv, code);
        String status = map.get("status").toString();
        if (StringUtils.equals(status, "1")) {
            //处理
            UserInfo userInfo   = JSONObject.parseObject(JSONObject.toJSONString(map.get("userInfo")).toString(), UserInfo.class);

            UserInfo userTemp = userInfoMapper.selectByPrimaryKey(userInfo);
            if (userTemp != null) {
                //用户信息更改
                if (!StringUtils.equals(JSONObject.toJSONString(userInfo), JSONObject.toJSONString(userTemp))) {
                    userInfoMapper.updateByPrimaryKeySelective(userInfo);
                }
            } else {
                userInfo.setTime(new Date());
                //插入新用户
                userInfoMapper.insert(userInfo);
            }
            return userInfo;
        } else {
            //抛出异常
            throw new UserException("验证失败");
        }
    }

    @Override
    public DetailVO userDetail(String openId) {

        //取出打卡记录
        UserClockInfo userClockInfo=userClockInfoMapper.selectByPrimaryKey(openId);
        if(userClockInfo==null)
        {
            userClockInfo=new UserClockInfo();
            userClockInfo.setScore(0f);
            userClockInfo.setInsistDay(0);
            userClockInfo.setOpenId(openId);
            userClockInfo.setTime(new Date());
            userClockInfo.setClockTime(new Date());
        }
        //查询当天打卡人数
        Example example=new Example(UserClockInfo.class);
        example.createCriteria()
                .andGreaterThanOrEqualTo("clockTime", DateUtils.getTodayDate());
        Integer duas=userClockInfoMapper.selectCountByExample(example);
        //查询当天发表日志数量
        Example example2=new Example(DailyRecord.class);
        example2.createCriteria()
                .andGreaterThanOrEqualTo("time",DateUtils.getTodayDate());
        Integer dailyCount=dailyRecordMapper.selectCountByExample(example2);
        //封装
        DetailVO detailVO=DetailVO.builder()
                .dailyCount(dailyCount)
                .duas(duas)
                .userClockInfo(userClockInfo)
                .build();
        return detailVO;
    }
}
