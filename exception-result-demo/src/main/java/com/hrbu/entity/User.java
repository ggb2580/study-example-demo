package com.hrbu.entity;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author Say my name
 */
@Data
public class User {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8,max = 20,message = "密码长度需要在 8 - 20 个字符之间")
    private String password;

    @NotNull(message = "年龄不能为空")
    @Min(value = 18,message = "年龄必须大于18岁")
    @Max(value = 60,message = "年龄不能超过60岁")
    private Integer age;

    @Email(message = "邮箱格式不正确")
    private String email;

}
