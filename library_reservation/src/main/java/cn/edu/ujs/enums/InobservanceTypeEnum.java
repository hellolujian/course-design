package cn.edu.ujs.enums;

/**
 * Created by DELL on 2017/12/29.
 */
public enum InobservanceTypeEnum {

    NOT_FIRST_SIGN_IN(1,"未进行第一次签到"),
    NOT_SIGN_OUT_BEFORE_FINAL_LEAVE(2,"最后离开未签离"),
    NOT_RETURN_AFTER_TEMPORARY_LEAVE(3,"临时签离未归"),
    NOT_SIGN_OUT_BEFORE_TEMPORARY_LEAVE(4,"临时离开未签离")
    ;

    private Integer code;
    private String message;

    InobservanceTypeEnum(Integer code, String message) {
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
