package com.gduf.clock.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "daily_comment")
@Data
@Builder
public class DailyComment {
    @Id
    private String id;

    @Column(name = "daily_map")
    private String dailyMap;

    @Column(name = "open_id")
    private String openId;

    private Date time;

    private String comment;

}