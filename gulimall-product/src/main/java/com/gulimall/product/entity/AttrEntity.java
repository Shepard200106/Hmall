package com.gulimall.product.entity;
//维护商品可选的属性参数，如“屏幕尺寸”、“颜色”、“处理器”
//
//支持基本属性、销售属性分类
//
//关联商品分类、规格分组

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pms_attr")
public class AttrEntity {
    @TableId
    private Long attrId;

    private String attrName;

    private Integer searchType; // 是否可检索：0=否，1=是

    private String icon;

    private String valueSelect; // 可选值列表，逗号分隔

    private Integer attrType;   // 属性类型：0=销售属性，1=基本属性

    private Long catelogId;

    private Integer showDesc;

    private Integer enable;
}
