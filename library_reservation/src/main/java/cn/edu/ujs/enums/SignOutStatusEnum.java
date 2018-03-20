package cn.edu.ujs.enums;

/**
 * Created by DELL on 2017/12/26.
 */
public enum SignOutStatusEnum implements CodeEnum {

    NOT_SIGN_OUT(0,"当天未签离"),
    TEMPORARY_SIGN_OUT(1,"临时签离"),
    FINAL_SIGN_OUT(2,"终了签离")
    ;

    private Integer code;
    private String message;

    SignOutStatusEnum(Integer code, String message) {
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
