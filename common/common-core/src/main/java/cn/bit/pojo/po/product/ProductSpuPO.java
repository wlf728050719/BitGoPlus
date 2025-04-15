package cn.bit.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品SPU实体类（Standard Product Unit）
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_spu", autoResultMap = true)
public class ProductSpuPO {
    /** SPU ID（雪花ID） */
    @TableId(value = "spu_id")
    private Long spuId;

    /** SPU编码（唯一业务标识） */
    @TableField("spu_code")
    private String spuCode;

    /** 产品名称 */
    @TableField("spu_name")
    private String spuName;

    /** 分类ID */
    @TableField("category_id")
    private Long categoryId;

    /** 品牌ID */
    @TableField("brand_id")
    private Long brandId;

    /** 店铺ID */
    @TableField("shop_id")
    private Long shopId;

    /** 主图URL */
    @TableField("main_image")
    private String mainImage;

    /** 子图URL（JSON数组格式） */
    @TableField("sub_images")
    private String subImages;

    /** 产品描述（富文本） */
    @TableField("description")
    private String description;

    /** 规格模板（JSON格式） */
    @TableField("spec_template")
    private String specTemplate;

    /** 总销量 */
    @TableField("sales")
    private Integer sales;

    /** 状态（1-上架，0-下架） */
    @TableField("status")
    private Integer status;

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
