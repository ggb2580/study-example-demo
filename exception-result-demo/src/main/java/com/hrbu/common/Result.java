package com.hrbu.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Say my name
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    /*
    * 返回码
    * */
    private Integer code;
    /*
    * 返回信息
    * */
    private String message;
    /*
    * 返回结果
    * */
    private T data;

    /*
    * 成功响应
    * */
    public static <T> Result<T> success(T data){
        return new Result<>(200,"success",data);
    }

    public static <T> Result<T> success(String message,T data){
        return new Result<>(200,message,data);
    }

    /*
    * 失败响应
    * */
    public static <T> Result<T> error(Integer code , String message){
        return new Result<>(code,message,null);
    }


}
