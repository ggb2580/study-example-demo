package com.clouddemo.consumerorderdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Say my name
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T>Result<T> success(){
        return new Result<T>(EnumStatus.OK.getValue(),EnumStatus.OK.getMessage(),null);
    }

    public static <T>Result<T> success(T data){
        return new Result<T>(EnumStatus.OK.getValue(),EnumStatus.OK.getMessage(),data);
    }

    public static <T>Result<T> error(Integer code,String msg){

        return  new Result<T>(code,msg,null);

    }

    public static <T>Result<T> error(Integer code,T data,String msg){
        return new Result<T>(code,msg,data);
    }

}
