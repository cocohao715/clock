package com.gduf.clock.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_clock_info")
@Data
public class UserClockInfo {
    @Id
    @Column(name = "open_id")
    private String openId;

    @Column(name = "insist_day")
    private Integer insistDay;

    private Float score;

    @Column(name = "clock_time")
    private Date clockTime;

    private Date time;

}