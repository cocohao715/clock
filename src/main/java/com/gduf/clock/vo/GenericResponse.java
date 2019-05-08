package com.gduf.clock.vo;

import com.alibaba.fastjson.JSON;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一JSON返回类
 * @author sxd
 * @since 2018/4/1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse implements Serializable {


    /**
     * 程序定义状态码
     */
    private int code;
    /**
     * 必要的提示信息
     */
    private String message;
    /**
     * 业务数据
     */
    private Object datas;

    /**
     * 对业务数据单独处理
     * @return
     */
    @Override
    public String toString() {
        if(Objects.isNull(this.datas)){
            this.setDatas(new Object());
        }
        return JSON.toJSONString(this);
    }
}