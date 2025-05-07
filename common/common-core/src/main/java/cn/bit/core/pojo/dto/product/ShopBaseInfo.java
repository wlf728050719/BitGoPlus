package cn.bit.core.pojo.dto.product;

import cn.bit.core.jsr303.annotation.ValidFile;
import cn.bit.core.jsr303.annotation.ValidString;
import cn.bit.core.jsr303.enums.FileEnum;
import cn.bit.core.jsr303.enums.StringEnum;
import cn.bit.core.pojo.po.product.ShopPO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>商店基本信息</p>
 * Date:2025/05/07 20:26:15
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class ShopBaseInfo {
    /** 店铺ID */
    @Null
    private Long shopId;

    /** 店铺名称 */
    @NotNull
    private String shopName;

    /** 店铺logo */
    @Null
    private String logoUrl;

    @ValidFile(value = FileEnum.IMAGE_FILE)
    private MultipartFile logoImg;

    /** 店铺描述 */
    private String description;

    /** 联系电话 */
    @NotNull
    @ValidString(StringEnum.PHONE_STRING)
    private String contactPhone;

    /** 店铺地址 */
    @NotNull
    private String address;

    /** 营业执照号 */
    @NotNull
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
        shopPO.setLogoUrl(logoUrl);
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
        this.logoUrl = shopPO.getLogoUrl();
        this.description = shopPO.getDescription();
        this.contactPhone = shopPO.getContactPhone();
        this.address = shopPO.getAddress();
        this.businessLicense = shopPO.getBusinessLicense();
        this.sortOrder = shopPO.getSortOrder();
        this.status = shopPO.getStatus();
        this.delFlag = shopPO.getDelFlag();
    }
}
