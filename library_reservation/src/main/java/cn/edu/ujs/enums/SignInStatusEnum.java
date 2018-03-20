package cn.edu.ujs.enums;

import org.aspectj.apache.bcel.classfile.Code;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

/**
 * Created by DELL on 2017/12/26.
 */
public enum SignInStatusEnum implements CodeEnum {

    NOT_SIGN_IN(0,"当天没有签到"),
    FIRST_SIGN_IN(1,"当天第一次签到"),
    SIGN_IN_AFTER_SIGN_OUT(2,"临时签离后签到")
    ;

    private Integer code;
    private String message;

    SignInStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
