package cn.edu.ujs.enums;

/**
 * Created by DELL on 2017/12/26.
 */
public enum SeatStatusEnum implements CodeEnum {

    NOT_BEING_RESERVED(0,"没有被预约"),
    BEING_RESERVED_WITHOUT_SIGN_IN(1,"被预约但是没有签到"),
    BEING_SIGN_IN(2,"被签到"),
    BEING_TEMPORARY_SIGN_OUT(3,"被临时签离"),
    NOT_USABLE(4,"禁用")
    ;

    private Integer code;
    private String message;

    SeatStatusEnum(Integer code, String message) {
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
