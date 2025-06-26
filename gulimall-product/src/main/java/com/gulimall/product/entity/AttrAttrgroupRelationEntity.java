package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//📦 模块作用：
//平台属性（如：屏幕尺寸） → 属于一个规格分组（如：基本参数）
//
//多对一 关系
//
//使用关联表维护绑定信息
@Data
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity {
    @TableId
    private Long id;

    private Long attrId;

    private Long attrGroupId;
}
