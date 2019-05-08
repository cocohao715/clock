package com.gduf.clock.controller;

import com.gduf.clock.entity.UserInfo;
import com.gduf.clock.exception.MyException;
import com.gduf.clock.exception.UserException;
import com.gduf.clock.service.UserService;
import com.gduf.clock.service.impl.UserServiceImpl;
import com.gduf.clock.vo.DetailVO;
import com.gduf.clock.vo.GenericResponse;
import com.gduf.clock.vo.ResponseFormat;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping(value = "login", produces = "application/json")
    @ApiOperation(value = "微信用户登陆", notes = "微信用户登陆")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "encryptedData", dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "iv", dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "code", dataType = "String", paramType = "query")
            }
    )
    public GenericResponse login(@Param("encryptedData") String encryptedData, @Param("iv")String iv, @Param("code")String code) {

        HashMap jsonData = new HashMap(16);
        if (StringUtil.isEmpty(encryptedData) || StringUtil.isEmpty(iv) || StringUtil.isEmpty(code)) {
            //参数出错
            throw new MyException(50002,new UserException("参数错误"));
        }

        try {
            UserInfo userInfo = userService.userLogin(encryptedData, iv, code);
            jsonData.put("openId", userInfo.getOpenId());
            return ResponseFormat.retParam(200,userInfo);
        } catch (Exception e) {
            log.info(e.toString());
            throw new MyException(40001,e);
        }

    }

    @ResponseBody
    @GetMapping(value = "detail", produces = "application/json")
    @ApiOperation(value = "获取个人信息", notes = "微信用户登陆")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "openId", dataType = "String", paramType = "query"),
            }
    )
    public GenericResponse detail(@Param("openId") String openId) {
        try {
            DetailVO detailVO=userService.userDetail(openId);
            return ResponseFormat.retParam(200,detailVO);
        } catch (Exception e) {
            throw new MyException(40001,e);
        }
    }
}
