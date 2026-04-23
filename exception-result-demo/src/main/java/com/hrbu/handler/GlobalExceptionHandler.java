package com.hrbu.handler;

import com.hrbu.common.ErrorCodeEnum;
import com.hrbu.common.Result;
import com.hrbu.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Say my name
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    //1.处理【@Valid】 校验异常（请求体 @RequestBody）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handlerMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("参数校验失败：{}", e.getMessage());
        //提取第一个校验失败的字段错误信息，返回给前端
        String errorMsg = e.getBindingResult().getFieldError() == null
                ? "参数校验失败"
                : e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(ErrorCodeEnum.INVALID_PARAM.getCode(), errorMsg);
    }

    // 2. 处理【@Validated + @RequestParam / @PathVariable】校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) {
        log.warn("请求参数校验失败: {}", e.getMessage());
        String errorMsg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("参数校验失败");
        return Result.error(ErrorCodeEnum.INVALID_PARAM.getCode(), errorMsg);
    }

    // 3. 处理自定义的业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, msg={}", e.getCode(), e.getMessage());
        // 可以在此进行更细致的业务异常逻辑处理，例如记录告警等
        return Result.error(e.getCode(), e.getMessage());
    }

    // 4. 兜底：处理所有未捕获的系统异常（500）
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleGlobalException(Exception e) {
        log.error("系统异常: ", e);
        // 返回一个通用的友好提示，避免暴露敏感信息
        return Result.error(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
    }
}
