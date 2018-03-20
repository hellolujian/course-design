package cn.edu.ujs.enums;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Created by DELL on 2017/12/26.
 */
public enum ReserveStatusEnum implements CodeEnum {

    NOT_RESERVED(0,"当天未预约"),
    RESERVED(1,"当天有预约"),
    ;

    private Integer code;
    private String message;

    ReserveStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
