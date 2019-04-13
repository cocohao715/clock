package com.gduf.clock.dao;

import com.gduf.clock.entity.DailyRecord;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DailyRecordMapper extends Mapper<DailyRecord> {
    List<DailyRecord> selectDailyMap(String dailyMap);
}