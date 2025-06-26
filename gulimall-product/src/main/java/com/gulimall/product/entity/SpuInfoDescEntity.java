package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pms_spu_info_desc")
public class SpuInfoDescEntity {
    @TableId
    private Long spuId;   // 对应SPU主键

    private String decript; // 商品介绍（逗号拼接的图文、文字等）
}
