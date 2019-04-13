package com.gduf.clock.dao;

import com.gduf.clock.entity.VideoInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoInfoMapper extends Mapper<VideoInfo> {
    List<VideoInfo> selectDailyMap(String dailyMap);
}