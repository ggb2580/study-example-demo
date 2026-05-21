package com.clouddemo.consumerorderdemo.common;

/**
 * @author Say my name
 */

public enum EnumStatus {

   OK(200,"操作成功"),
   ERROR(-1,"操作失败"),
   NO_FOUND(-2,"没有找到对应数据");

   private Integer value;
   private String message;

   EnumStatus(Integer value,String message){
       this.value = value;
       this.message = message;
   }

   public Integer getValue() {
       return value;
   }

   public void setValue(Integer value) {
       this.value = value;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(String message) {
       this.message = message;
   }



}