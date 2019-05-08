package com.gduf.clock.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IDEA
 * spring 对于 RuntimeException 异常才会进行事务回滚。
 * @author HaoChen
 * @Date 2019/4/24 13:08
 */

@Getter
@Setter
public class MyException extends RuntimeException {

    public MyException(Integer code, Exception exception) {
        this.code = code;
        this.exception = exception;
    }

    private Integer code;
    private Exception exception;
}