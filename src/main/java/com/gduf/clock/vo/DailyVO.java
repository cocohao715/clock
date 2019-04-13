package com.gduf.clock.vo;

import com.gduf.clock.entity.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author HaoChen
 * @Date 2019/4/7 19:35
 */
@Data
@Builder
public class DailyVO {
    private DailyRecord dailyRecord;
    private DailyLikes dailyLikes;
    private List<DailyComment> dailyComments;
    private List<ImageInfo> imageInfos;
    private List<SpeechInfo> speechInfos;
    private List<VideoInfo> videoInfos;
}
