package com.gulimall.common.utils;
/**
 * 登录用户上下文工具类
 * 存储当前线程中的 userId（每个请求线程隔离）
 */
public class UserContext {
    private static final ThreadLocal<Long> User_ID_HOLDER = new ThreadLocal<>();

    // 设置 userId
    public static void setUser_ID_HOLDER(Long userId) {
        User_ID_HOLDER.set(userId);
    }
    // 获取 userId
    public  static Long getUser_ID_HOLDER() {
        return User_ID_HOLDER.get();
    }
    // 清除（防止内存泄露）
    public static void clear(){
        User_ID_HOLDER.remove();
    }
}
