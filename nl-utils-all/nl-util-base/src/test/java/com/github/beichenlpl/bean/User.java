package com.github.beichenlpl.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.29
 */
@Data
@Accessors(chain = true)
public class User {
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private String phone;
    private String email;
}
