package com.gulimall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.exception.BizException;
import com.gulimall.common.utils.JwtUtils;
import com.gulimall.common.utils.R;
import com.gulimall.user.dto.LoginRequest;
import com.gulimall.user.dto.RegisterRequest;
import com.gulimall.user.entity.UserEntity;
import com.gulimall.user.mapper.UserMapper;
import com.gulimall.user.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(RegisterRequest request) {
        // 1. 从 Redis 获取验证码
        String redisCode = redisTemplate.opsForValue().get("verify:code:" + request.getPhone());
//         2. 校验验证码是否存在/正确
        System.out.println("✅ redisCode 已加载！");
        System.out.println(redisCode);
        System.out.println(request.getCode());
        if(redisCode == null || !redisCode.equals(request.getCode())) {
            throw new BizException(400,"验证码错误，或者已过期");
        }

        // 3. 查询数据库，判断手机号是否已存在
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.eq(UserEntity::getPhone, request.getPhone());
        if(userMapper.selectCount(query)> 0){
            throw new BizException(400,"手机号已经被注册");
        }

        // 4. 保存新用户到数据库
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setPhone(request.getPhone());
        userMapper.insert(userEntity);

        // 5. 删除 Redis 中验证码（防止重复提交）
        redisTemplate.delete("verify:code:" + request.getPhone());
    }
    @Override
    public R login(LoginRequest request){
        String redisCode = redisTemplate.opsForValue().get("verify:code:"+request.getPhone());
//        System.out.println("✅ redisCode 已加载！");
        if(redisCode == null || !redisCode.equals(request.getCode())) {
            throw new BizException(400,"验证码错误或者已经过期");
        }
        // 查询用户是否已存在
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.eq(UserEntity::getPhone, request.getPhone());
//        System.out.println("✅ query！");

        UserEntity user = userMapper.selectOne(query);
//        System.out.println(user);
        // 如果不存在，则自动注册
        if(user == null){
            user = new UserEntity();
            user.setPhone(request.getPhone());
            user.setUsername("用户" + UUID.randomUUID().toString().substring(0, 6));
            userMapper.insert(user);
        }

        // 删除验证码
        redisTemplate.delete("verify:code:" + request.getPhone());
        // 生成 token
//        System.out.println("验证码已经删除");
        String token = JwtUtils.generateToken(user.getId(),user.getUsername());
//        System.out.println("✅ token 已生成！");
        // 返回 token 和用户信息
        return R.ok()
                .put("token",token)
                .put("userId",user.getId())
                .put("userName",user.getUsername());
    }
}
