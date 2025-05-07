package cn.bit.userservice.manager.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.bit.core.pojo.po.user.UserPO;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.manager.UserManager;
import lombok.AllArgsConstructor;

import java.util.List;

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
    public List<UserPO> selectUserPOsByUserName(String userName) {
        return userMapper.selectUserPOsByUserName(userName);
    }
}
