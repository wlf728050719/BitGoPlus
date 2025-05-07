package cn.bit.userservice.manager;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.bit.core.pojo.po.user.UserPO;

import java.util.List;

public interface UserManager extends IService<UserPO> {
    Long insertUser(UserPO userPO);

    UserPO selectUserPOByUserId(Long userId);

    List<UserPO> selectUserPOsByUserName(String userName);

    UserPO selectUndeletedUserPOByVerifiedEmail(String email);

}
