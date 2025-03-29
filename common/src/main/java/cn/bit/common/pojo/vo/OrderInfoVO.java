package cn.bit.common.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVO {
    private Integer orderId;
    private String productName;
    private Integer amount;
    private Date createTime;
    private String buyerName;
    private String sellerName;
    private String getLocation;
}
