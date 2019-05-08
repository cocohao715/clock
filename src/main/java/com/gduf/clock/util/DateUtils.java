package com.gduf.clock.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IDEA
 *
 * @author HaoChen
 * @Date 2019/4/24 10:55
 */
@Slf4j
public class DateUtils {
    /**
     * @Description 是否为当天24h内
     * @author fighting9707
     * @param inputJudgeDate 要判断是否在当天24h内的时间
     * @return boolean
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取今天零点零分零秒
     * @return Date
     */
    public static Date getTodayDate()
    {
        //当前时间的时间戳
        Long  time = System.currentTimeMillis();
        long zero = time/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        //今天零点零分零秒
        return new Date(zero);
    }
}