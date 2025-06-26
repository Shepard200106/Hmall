package com.gulimall.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
