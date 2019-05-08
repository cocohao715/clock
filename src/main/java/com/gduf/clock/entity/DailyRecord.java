package com.gduf.clock.entity;

import java.util.Date;
import javax.persistence.*;


@Table(name = "daily_record")
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

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return daily_map
     */
    public String getDailyMap() {
        return dailyMap;
    }

    /**
     * @param dailyMap
     */
    public void setDailyMap(String dailyMap) {
        this.dailyMap = dailyMap;
    }

    /**
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return insist_day
     */
    public Integer getInsistDay() {
        return insistDay;
    }

    /**
     * @param insistDay
     */
    public void setInsistDay(Integer insistDay) {
        this.insistDay = insistDay;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}