package cn.bit.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品图片实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_image", autoResultMap = true)
public class ProductImagePO {
    /** 图片ID */
    @TableId(value = "image_id")
    private Long imageId;

    /** 关联的SPU ID */
    @TableField("spu_id")
    private Long spuId;

    /** 关联的SKU ID（null表示SPU通用图片） */
    @TableField("sku_id")
    private Long skuId;

    /** 图片URL */
    @TableField("image_url")
    private String imageUrl;

    /** 排序权重（数字越小越靠前） */
    @TableField("sort_order")
    private Integer sortOrder;

    /** 是否主图（1-是，0-否） */
    @TableField("is_main")
    private Integer isMain;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    @TableField("del_flag")
    private Integer delFlag;
}
