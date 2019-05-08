package com.gduf.clock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_info")
@Data
@JsonIgnoreProperties(value={"time"})
public class UserInfo {
    @Id
    @Column(name = "open_id")
    private String openId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String gender;

    private String country;

    private String province;

    private String city;

    private String language;

    private Date time;

}