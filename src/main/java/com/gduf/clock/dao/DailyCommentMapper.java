package com.gduf.clock.dao;

import com.gduf.clock.entity.DailyComment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DailyCommentMapper extends Mapper<DailyComment> {
    List<DailyComment> selectDailyMap(String dailyMap);
}