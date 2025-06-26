package com.gulimall.user.controller;

import com.gulimall.common.utils.R;
import com.gulimall.common.utils.UserContext;
import com.gulimall.user.dto.LoginRequest;
import com.gulimall.user.dto.RegisterRequest;
import com.gulimall.user.entity.UserEntity;
import com.gulimall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/{id}")
    public R getUser(@PathVariable("id") Long id) {
        UserEntity user = userService.getById(id);
        return R.ok().put("user", user);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterRequest request) {
        // 调用 service 层处理注册逻辑

        userService.register(request);
        return R.ok().put("message","注册成功");
    }

    @GetMapping("/send-code/{phone}")
    public R sendCode(@PathVariable("phone") String phone) {
        String code = "123456";
        redisTemplate.opsForValue().set("verify:code:" + phone, code, 5, TimeUnit.MINUTES);
        return R.ok().put("code", code);
    }

    @GetMapping("/me")
    public R me() {
        Long userId = UserContext.getUser_ID_HOLDER(); // ✅ 无需再查 token、数据库
        return R.ok().put("userId", userId);
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginRequest request) {
        R res = userService.login(request);
//        System.out.println("token返回");
//        System.out.println(res);
        return R.ok().put("message","登录/注册成功")
                    .put("user", res);
    }
}
