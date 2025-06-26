package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 商品分类实体类，对应数据库表：pms_category
 */
@Data
@TableName("pms_category") // 指定表名
public class CategoryEntity {
    /**
     * 分类ID（主键）
     */
    @TableId
    private Long catId;
    /**
     * 分类名称，如：手机、电脑
     */
    private String name;
    /**
     * 父分类ID，若是一级分类则为 0
     */
    private Long parentCid;
    /**
     * 分类层级，1=一级，2=二级，3=三级
     */
    private Integer catLevel;
    /**
     * 是否显示：0=不显示，1=显示
     */
    private Integer showStatus;
    /**
     * 排序字段，值越小越靠前
     */
    private Integer sort;
    /**
     * 子分类集合（非数据库字段，用于返回树结构）
     */
    @TableField(exist = false)
    private List<CategoryEntity> children;
}

