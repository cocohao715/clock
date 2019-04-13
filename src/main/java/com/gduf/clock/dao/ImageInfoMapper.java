package com.gduf.clock.dao;

import com.gduf.clock.entity.ImageInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ImageInfoMapper extends Mapper<ImageInfo> {
    List<ImageInfo> selectDailyMap(String dailyMap);
}