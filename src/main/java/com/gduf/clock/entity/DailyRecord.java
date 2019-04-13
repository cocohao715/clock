package com.gduf.clock.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "daily_record")
@Data
@Builder
public class DailyRecord {
    @Id
    private String id;

    @Column(name = "daily_map")
    private String dailyMap;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "insist_day")
    private Integer insistDay;

    private Date time;

    private String content;

}