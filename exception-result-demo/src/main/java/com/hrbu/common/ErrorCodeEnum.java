package com.hrbu.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    // 通用异常
    SUCCESS(200, "success"),
    SYSTEM_ERROR(500, "系统内部异常，请稍后再试"),
    INVALID_PARAM(400, "参数校验失败"),

    // 业务异常 (根据业务划分区间，例如1000-1999为用户模块)
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_STATUS_INVALID(1002, "用户状态异常，已被冻结");

    private final Integer code;
    private final String message;

}
