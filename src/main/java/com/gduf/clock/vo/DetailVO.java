package com.gduf.clock.vo;

import com.gduf.clock.entity.UserClockInfo;
import lombok.Builder;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author HaoChen
 * @Date 2019/4/24 11:02
 */
@Data
@Builder
public class DetailVO {
    /**
     * 用户坚持天数
     */
    private UserClockInfo userClockInfo;
    /**
     * 发表日志数量
     */
    private Integer dailyCount;
    /**
     * 日活跃人数
     */
    private Integer duas;

}
