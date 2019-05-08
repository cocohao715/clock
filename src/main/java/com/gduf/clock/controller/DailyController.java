package com.gduf.clock.controller;


import com.gduf.clock.service.DailyService;
import com.gduf.clock.service.impl.DailyServiceImpl;
import com.gduf.clock.vo.DailyVO;
import com.gduf.clock.vo.GenericResponse;
import com.gduf.clock.vo.ResponseFormat;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;


@RestController
@Slf4j
public class DailyController {

    private DailyService dailyService;
    @Value("${web.upload.image.type}")
    private int imageType;
    @Value("${web.upload.video.type}")
    private int videoType;
    @Value("${web.upload.speech.type}")
    private int speechType;
    public DailyController(DailyServiceImpl dailyService) {
        this.dailyService = dailyService;

    }

    @ApiOperation(value = "发表日志的附件上传", notes = "发表日志的附件上传")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "file", dataType = "String", paramType = "query", value = "文件"),
                    @ApiImplicitParam(name = "dailyMap", dataType = "String", paramType = "query", value = "与日志信息对应关系"),
                    @ApiImplicitParam(name = "openId", dataType = "String", paramType = "query", value = "openId"),
                    @ApiImplicitParam(name = "type", dataType = "int", paramType = "query", value = "1:图片;2:语音;3:视频")
            }
    )
    @PostMapping(value = "upload", produces = "application/json; charset=utf-8")
    @ResponseBody
    public GenericResponse upload(@RequestParam("file") MultipartFile[] files,
                                 @RequestParam("dailyMap") String dailyMap,
                                 @RequestParam("openId") String openId,
                                 @RequestParam("type") int type) {
        try {
            if (type == imageType) {
                dailyService.uploadImage(files, dailyMap, openId);
            } else if (type == videoType) {
                dailyService.uploadSpeech(files, dailyMap, openId);
            } else if (type == speechType) {
                dailyService.uploadVideo(files, dailyMap, openId);
            }

        } catch (Exception e) {

            log.info(e.toString());
            return  ResponseFormat.retParam(40001,e);
        }
        return ResponseFormat.retParam(200,null);
    }


    @ApiOperation(value = "提交日志内容", notes = "提交日志内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "openId", dataType = "String", paramType = "query", value = "openId"),
                    @ApiImplicitParam(name = "content", dataType = "String", paramType = "query", value = "日志内容"),
                    @ApiImplicitParam(name = "dailyMap", dataType = "String", paramType = "query", value = "与日志信息对应关系")
            }
    )
    @PostMapping(value = "upContent", produces = "application/json; charset=utf-8")
    @ResponseBody
    public GenericResponse upContent(@RequestParam("openId") String openId,
                                    @RequestParam("content") String content,
                                    @RequestParam("dailyMap") String dailyMap) {
        try {
            if (StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(openId) && StringUtil.isNotEmpty(dailyMap)) {
                //累加打卡天数和分数
                dailyService.addInsistDay(openId);
                //上传发表内容
                dailyService.upContent(openId, dailyMap, content);

            }
        } catch (Exception e) {
            log.info(e.toString());
            return  ResponseFormat.retParam(40001,e);
        }
        return ResponseFormat.retParam(200,null);
    }


    @ApiOperation(value = "获取发表日志", notes = "获取发表日志")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "openId", dataType = "String", paramType = "query", value = "用户标识"),
                    @ApiImplicitParam(name = "pageNum", dataType = "String", paramType = "query", value = "页码"),
            }
    )
    @GetMapping(value = "obtain", produces = "application/json; charset=utf-8")
    @ResponseBody
    public GenericResponse obtain(@Param(value = "openId") String openId, @RequestParam(defaultValue = "1")int pageNum) {

        try {
            List<DailyVO> dailyVO = dailyService.obtain(openId, pageNum);
            return  ResponseFormat.retParam(200,dailyVO);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return  ResponseFormat.retParam(40001,e);
        }

    }

}
