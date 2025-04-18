package cn.bit.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.bit.pojo.po.user.UserPO;

public interface UserMapper extends BaseMapper<UserPO> {
    UserPO selectUserByUserName(String userName);
    UserPO selectUserByUserId(Long userId);
    UserPO selectAvailableUserByUserName(String userName);
    UserPO selectAvailableUserByUserId(Long userId);
}
