package com.clouddemo.paymentdemo.entity;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Say my name
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMsg  implements Serializable{
    /*
    * 订单号
    * */
    private String orderId;
    /*
    * 用户ID
    * */
    private Long userId;
    /*
    * 手机号
    * */
    private String phone;
    /*
    * 商品ID
    * */
    private Long goodsId;
    /*
    * 购买数量
    * */
    private Integer num;
}
