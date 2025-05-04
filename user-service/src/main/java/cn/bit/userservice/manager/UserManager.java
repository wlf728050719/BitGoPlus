package cn.bit.userservice.manager;

import cn.bit.core.pojo.dto.UserBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.bit.core.pojo.po.user.UserPO;

public interface UserManager extends IService<UserPO> {
    Long insert(UserPO userPO);

    UserPO selectUserPOByUserId(Long userId);

    UserPO selectUndeletedUserPOByVerifiedEmail(String email);

    UserPO selectUndeletedUserPOByUserName(String userName);

    UserBaseInfo selectUndeletedBaseUserInfoByUserId(Long userId);

    UserBaseInfo selectAvailableBaseUserInfoByUserName(String userName);

    UserBaseInfo selectAvailableBaseUserInfoByUserId(Long userId);

}
