package com.zzw.entity.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    /**
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 
     */
    private String sex;
}