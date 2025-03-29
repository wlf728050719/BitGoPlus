package cn.bit.common.pojo.dto;

import cn.bit.common.pojo.po.UserPO;
import lombok.Data;

@Data
public class UserBaseInfoDTO {
    private Integer id;
    private String username;
    private String account;

    public UserBaseInfoDTO() {}
    public UserBaseInfoDTO(UserPO userPO) {
        this.id = userPO.getId();
        this.username = userPO.getUsername();
        this.account = userPO.getAccount();
    }

}
