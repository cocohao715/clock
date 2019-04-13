package com.gduf.clock.service.impl;

import com.gduf.clock.dao.*;
import com.gduf.clock.entity.*;
import com.gduf.clock.service.DailyService;
import com.gduf.clock.vo.DailyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * author:HaoChen
 * Date:2019/3/23
 * Time:16:53
 */
@Service
public class DailyServiceImpl implements DailyService {
    ImageInfoMapper imageInfoMapper;
    SpeechInfoMapper speechInfoMapper;
    VideoInfoMapper videoInfoMapper;
    DailyRecordMapper dailyRecordMapper;
    DailyLikesMapper dailyLikesMapper;
    DailyCommentMapper dailyCommentMapper;
    @Value("${web.upload.image.path}")
    private String imagePath;
    @Value("${web.upload.video.path}")
    private String videoPath;
    @Value("${web.upload.speech.path}")
    private String speechPath;

    public DailyServiceImpl(ImageInfoMapper imageInfoMapper, VideoInfoMapper videoInfoMapper,
                            SpeechInfoMapper speechInfoMapper, DailyRecordMapper dailyRecordMapper,
                            DailyLikesMapper dailyLikesMapper, DailyCommentMapper dailyCommentMapper) {
        this.imageInfoMapper = imageInfoMapper;
        this.speechInfoMapper = speechInfoMapper;
        this.videoInfoMapper = videoInfoMapper;
        this.dailyRecordMapper = dailyRecordMapper;
        this.dailyLikesMapper = dailyLikesMapper;
        this.dailyCommentMapper = dailyCommentMapper;
    }

    @Override
    public void uploadImage(MultipartFile[] files, String dailyMap, String openId) {
        //保存图片
        String[] fileNames = upload(files, imagePath);
        for (String fileName : fileNames) {
            ImageInfo imageInfo = ImageInfo.builder()
                    .dailyMap(dailyMap)
                    .id(UUID.randomUUID().toString())
                    .openId(openId)
                    .imgPath(fileName)
                    .time(new Date())
                    .build();
            imageInfoMapper.insert(imageInfo);
        }
    }

    @Override
    public void uploadVideo(MultipartFile[] files, String dailyMap, String openId) {
        //保存录像
        String[] fileNames = upload(files, videoPath);
        for (String fileName : fileNames) {
            VideoInfo videoInfo = VideoInfo.builder()
                    .dailyMap(dailyMap)
                    .id(UUID.randomUUID().toString())
                    .openId(openId)
                    .videoPath(fileName)
                    .time(new Date())
                    .build();
            videoInfoMapper.insert(videoInfo);
        }
    }

    @Override
    public void uploadSpeech(MultipartFile[] files, String dailyMap, String openId) {
        //保存语音
        String[] fileNames = upload(files, speechPath);
        for (String fileName : fileNames) {
            SpeechInfo speechInfo = SpeechInfo.builder()
                    .dailyMap(dailyMap)
                    .id(UUID.randomUUID().toString())
                    .openId(openId)
                    .speechPath(fileName)
                    .time(new Date())
                    .build();
            speechInfoMapper.insert(speechInfo);
        }

    }

    @Override
    public void upContent(String openId, String dailyMap, String content) {

        DailyRecord dailyRecord = DailyRecord.builder().
                dailyMap(dailyMap).content(content).openId(openId).id(UUID.randomUUID().toString()).build();
        dailyRecordMapper.insert(dailyRecord);
    }

    @Override
    public List obtain(String openId, int pageNum) {

        List<DailyRecord> dailyRecords = getDailyRecords(pageNum);
        if (dailyRecords != null || !dailyRecords.isEmpty()) {
            List<DailyVO> dailyVOS = dailyRecords.stream().map((dailyRecord) -> {

                        //取出对应关系
                        String dailyMap = dailyRecord.getDailyMap();
                        //查询
                        List<ImageInfo> imageInfos = imageInfoMapper.selectDailyMap(dailyMap);
                        List<SpeechInfo> speechInfos = speechInfoMapper.selectDailyMap(dailyMap);
                        List<VideoInfo> videoInfos = videoInfoMapper.selectDailyMap(dailyMap);
                        List<DailyComment> dailyComments = dailyCommentMapper.selectDailyMap(dailyMap);
                        DailyLikes dailyLikes = dailyLikesMapper.selectDailyMap(dailyMap);
                        //封装

                        DailyVO dailyVO = DailyVO.builder()
                                .dailyRecord(dailyRecord)
                                .imageInfos(imageInfos)
                                .speechInfos(speechInfos)
                                .videoInfos(videoInfos)
                                .dailyComments(dailyComments)
                                .dailyLikes(dailyLikes).build();
                        return dailyVO;
                    }

            ).collect(Collectors.toList());
            return dailyVOS;
        }
        return new LinkedList();
    }

    public List<DailyRecord> getDailyRecords(int pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<DailyRecord> dailyRecords = dailyRecordMapper.selectAll();
        PageInfo<DailyRecord> pageInfo = new PageInfo<DailyRecord>(dailyRecords);
        return pageInfo.getList();
    }

    public String[] upload(MultipartFile[] files, String uploadPath) {
        String[] fileNames = new String[files.length];
        //多文件上传
        if (files != null && files.length >= 1) {
            BufferedOutputStream bw = null;

            try {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getOriginalFilename();

                    //判断是否有文件(实际生产中要判断是否是音频文件)
                    if (StringUtil.isNotEmpty(fileNames[i])) {
                        //创建输出文件对象
                        File outFile = new File(uploadPath + fileNames[i]);
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(files[i].getInputStream(), outFile);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return fileNames;
    }
}
