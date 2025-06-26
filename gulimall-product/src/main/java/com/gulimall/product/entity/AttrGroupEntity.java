package com.gulimall.product.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 规格参数分组实体类
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity {
    @TableId
    private Long attrGroupId;

    private String attrGroupName;

    private Integer sort;

    private String descript;

    private String icon;

    private Long catelogId; // 所属分类ID
}
