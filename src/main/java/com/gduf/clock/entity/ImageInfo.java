package com.gduf.clock.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "image_info")
@Data
public class ImageInfo {
    @Id
    private String id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "daily_map")
    private String dailyMap;

    @Column(name = "img_path")
    private String imgPath;

    private Date time;

}