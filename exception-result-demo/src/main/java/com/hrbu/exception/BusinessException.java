package com.hrbu.exception;

import com.hrbu.common.ErrorCodeEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author Say my name
 */
@Getter
public class BusinessException extends RuntimeException{
    private final Integer code;
    private final String message;

    public BusinessException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum,String detailMessage){
        super(detailMessage);
        this.code = errorCodeEnum.getCode();
        this.message = detailMessage;
    }
}
