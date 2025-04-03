package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 商品SPU实体类（Standard Product Unit）
 */
@Data
@Accessors(chain = true)
public class ProductSpuPO {
    /** SPU ID（雪花ID） */
    private Long spuId;

    /** SPU编码（唯一业务标识） */
    private String spuCode;

    /** 产品名称 */
    private String spuName;

    /** 分类ID */
    private Long categoryId;

    /** 品牌ID */
    private Long brandId;

    /** 主图URL */
    private String mainImage;

    /** 子图URL（JSON数组格式） */
    private String subImages;

    /** 产品描述（富文本） */
    private String description;

    /** 规格模板（JSON格式） */
    private String specTemplate;

    /** 总销量 */
    private Integer sales = 0;

    /** 状态（1-上架，0-下架） */
    private Integer status = 1;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag = 0;
}
