package cn.bit.core.pojo.dto;

import cn.bit.core.pojo.po.product.ShopPO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
public class ShopBaseInfo {
    /** 店铺ID */
    @Null
    private Long shopId;

    /** 店铺名称 */
    private String shopName;

    /** 店铺logo */
    private String logo;

    /** 店铺描述 */
    private String description;

    /** 联系电话 */
    private String contactPhone;

    /** 店铺地址 */
    private String address;

    /** 营业执照号 */
    private String businessLicense;

    /** 排序 */
    @Null
    private Integer sortOrder;

    /** 状态（1-正常，0-禁用） */
    @Null
    private Integer status;

    /** 删除标志（0-未删除，1-已删除） */
    @Null
    private Integer delFlag;

    public ShopPO newShopPO() {
        ShopPO shopPO = new ShopPO();
        shopPO.setShopId(shopId);
        shopPO.setShopName(shopName);
        shopPO.setLogo(logo);
        shopPO.setDescription(description);
        shopPO.setContactPhone(contactPhone);
        shopPO.setAddress(address);
        shopPO.setBusinessLicense(businessLicense);
        shopPO.setSortOrder(sortOrder);
        shopPO.setStatus(status);
        shopPO.setDelFlag(delFlag);
        return shopPO;
    }

    public ShopBaseInfo(ShopPO shopPO) {
        this.shopId = shopPO.getShopId();
        this.shopName = shopPO.getShopName();
        this.logo = shopPO.getLogo();
        this.description = shopPO.getDescription();
        this.contactPhone = shopPO.getContactPhone();
        this.address = shopPO.getAddress();
        this.businessLicense = shopPO.getBusinessLicense();
        this.sortOrder = shopPO.getSortOrder();
        this.status = shopPO.getStatus();
        this.delFlag = shopPO.getDelFlag();
    }
}
