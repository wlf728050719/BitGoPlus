package cn.bit.common.pojo.po;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPO {
    private Integer id;
    private Date createTime;
    private Integer status;
    private Integer productId;
    private Integer sellerId;
    private Integer buyerId;
    private Integer amount;
    private String getLocation;
}
