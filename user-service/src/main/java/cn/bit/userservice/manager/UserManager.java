package cn.bit.userservice.manager;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.bit.pojo.po.user.UserPO;

import java.util.List;

public interface UserManager extends IService<UserPO> {
    Long insert(UserPO userPO);
    void updatePasswordByAvailableUsername(String username, String newPassword);
    UserPO selectUserByUserName(String userName);
    UserPO selectUserByUserId(Long userId);
    UserPO selectAvailableUserByUserName(String userName);
    UserPO selectAvailableUserByUserId(Long userId);
    List<UserPO> selectUndeletedUsersByVerifiedEmail(String email);
}
