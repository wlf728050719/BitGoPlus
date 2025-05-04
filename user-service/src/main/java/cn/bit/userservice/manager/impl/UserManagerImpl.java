package cn.bit.userservice.manager.impl;

import cn.bit.core.pojo.dto.UserBaseInfo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.bit.core.pojo.po.user.UserPO;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.manager.UserManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserManagerImpl extends ServiceImpl<UserMapper, UserPO> implements UserManager {
    private final UserMapper userMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public Long insertUser(UserPO userPO) {
        Long id = idGenerator.nextId();
        userPO.setUserId(id);
        save(userPO);
        return id;
    }

    @Override
    public UserPO selectUserPOByUserId(Long userId) {
        return userMapper.selectUserPOByUserId(userId);
    }

    @Override
    public UserPO selectUndeletedUserPOByVerifiedEmail(String email) {
        return userMapper.selectUndeletedUserPOByVerifiedEmail(email);
    }

    @Override
    public UserPO selectUndeletedUserPOByUserName(String userName) {
        return userMapper.selectUndeletedUserPOByUserName(userName);
    }

    @Override
    public UserBaseInfo selectUndeletedBaseUserInfoByUserId(Long userId) {
        return userMapper.selectUndeletedBaseUserInfoByUserId(userId);
    }

    @Override
    public UserBaseInfo selectAvailableBaseUserInfoByUserName(String userName) {
        return userMapper.selectAvailableBaseUserInfoByUserName(userName);
    }

    @Override
    public UserBaseInfo selectAvailableBaseUserInfoByUserId(Long userId) {
        return userMapper.selectAvailableBaseUserInfoByUserId(userId);
    }
}
