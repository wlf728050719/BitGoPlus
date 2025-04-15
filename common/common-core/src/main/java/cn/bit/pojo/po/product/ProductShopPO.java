package cn.bit.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 店铺实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_shop", autoResultMap = true)
public class ProductShopPO {
    /** 店铺ID */
    @TableId(value = "shop_id")
    private Long shopId;

    /** 店铺名称 */
    @TableField("shop_name")
    private String shopName;

    /** 店铺logo */
    @TableField("logo")
    private String logo;

    /** 店铺描述 */
    @TableField("description")
    private String description;

    /** 联系电话 */
    @TableField("contact_phone")
    private String contactPhone;

    /** 店铺地址 */
    @TableField("address")
    private String address;

    /** 营业执照号 */
    @TableField("business_license")
    private String businessLicense;

    /** 排序 */
    @TableField("sort_order")
    private Integer sortOrder;

    /** 状态（1-正常，0-禁用） */
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
