package com.gduf.clock.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "daily_likes")
@Data
@Builder
public class DailyLikes {
    @Id
    @Column(name = "open_id")
    private String openId;

    @Id
    private Date time;

    private String id;

    @Column(name = "nick_name")
    private String nickName;

    private Integer likes;

    @Column(name = "daily_map")
    private String dailyMap;


}