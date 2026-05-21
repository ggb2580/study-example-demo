package com.clouddemo.consumerorderdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Say my name
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int id;
    private String serialNo;
    private String money;

}
