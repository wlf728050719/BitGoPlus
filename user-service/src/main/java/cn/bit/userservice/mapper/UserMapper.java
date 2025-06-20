package cn.bit.userservice.mapper;

import cn.bit.core.pojo.dto.user.UserBaseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.bit.core.pojo.po.user.UserPO;

import java.util.List;

public interface UserMapper extends BaseMapper<UserPO> {
    UserPO selectUserPOByUserId(Long userId);

    UserPO selectUndeletedUserPOByVerifiedEmail(String email);

    List<UserPO> selectUserPOsByUserName(String userName);

    UserBaseInfo selectUndeletedBaseUserInfoByUserId(Long userId);

    UserBaseInfo selectAvailableBaseUserInfoByUserName(String userName);

    UserBaseInfo selectAvailableBaseUserInfoByUserId(Long userId);
}
