package com.gulimall.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.utils.R;
import com.gulimall.user.dto.LoginRequest;
import com.gulimall.user.dto.RegisterRequest;
import com.gulimall.user.entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    void register(RegisterRequest request); // 👈 这行是声明方法
    R login(LoginRequest request);
}
