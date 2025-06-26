package com.gulimall.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("user")
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private Date createTime;
}
