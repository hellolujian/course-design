package cn.edu.ujs.exception;

import cn.edu.ujs.enums.ResultEnum;

/**
 * Created by DELL on 2017/12/27.
 */
public class ReserveException extends RuntimeException {

    private Integer code;

    public ReserveException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ReserveException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
