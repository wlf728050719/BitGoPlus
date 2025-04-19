package cn.bit.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.bit.pojo.po.user.UserPO;

import java.util.List;

public interface UserMapper extends BaseMapper<UserPO> {
    UserPO selectUserByUserName(String userName);
    UserPO selectUserByUserId(Long userId);
    UserPO selectAvailableUserByUserName(String userName);
    UserPO selectAvailableUserByUserId(Long userId);
    List<UserPO> selectUsersByVerifiedEmail(String email);
}
