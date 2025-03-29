package cn.bit.common.pojo.vo;

import cn.bit.common.pojo.po.UserPO;
import lombok.Data;

@Data
public class UserFavorVO {
    private String sport;
    private String fruit;

    public UserFavorVO(UserPO userPO) {
        this.sport = userPO.getSport();
        this.fruit = userPO.getFruit();
    }
}
