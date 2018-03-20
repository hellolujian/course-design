package cn.edu.ujs.enums;

/**
 * Created by DELL on 2017/12/27.
 */
public enum ResultEnum {

    SUCCESS_RESERVE(0,"预约成功"),
    FORBID_RESERVE(1,"你已经被禁止预约"),
    ALREADY_RESERVE(2,"当天你已经预约过"),
    RESERVED_BY_OTHERS(3,"该座位已被其他人预约"),

    NOT_RESERVED(4,"当天还未预约"),
    BEING_RESERVED(5,"该座位已被预约"),

    SUCCESS_SIGN_IN(10,"签到成功"),
    ALREAEDY_SIGN_IN(11,"已经签到"),

    NOT_SIGN_IN(12,"当天还未签到"),

    SUCCESS_TEMPORARY_SIGN_OUT(20,"临时签离成功"),
    NOT_TEMPORARY_SIGN_OUT(21,"离开未进行临时签离"),
    ALREADY_TEMPORARY_SIGN_OUT(22,"已经临时签离"),
    ALREADY_FINAL_SIGN_OUT(23,"当天已签离"),
    SUCCESS_SIGN_OUT(24,"签离成功"),


    SUCCESS_LOGIN(30,"登录成功"),
    ERROR_LOGIN(31,"用户名或密码不正确"),
    SUCCESS_LOGIN_OUT(32,"登出成功")
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
