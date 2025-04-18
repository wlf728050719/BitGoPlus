package cn.bit.userservice.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.bit.pojo.po.user.UserPO;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
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
    public Long insert(UserPO userPO) {
        Long id = idGenerator.nextId();
        userPO.setUserId(id);
        userMapper.insert(userPO);
        return id;
    }

    @Override
    public void updatePasswordByAvailableUsername(String username, String newPassword) {
        UserPO userPO = selectAvailableUserByUserName(username);
        userPO.setPassword(newPassword);
        updateById(userPO);
    }

    @Override
    public List<UserPO> selectUndeletedUsersByVerifiedEmail(String email) {
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("email_verify", 1).eq("del_flag", 0);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public UserPO selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public UserPO selectUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public UserPO selectAvailableUserByUserName(String userName) {
        return userMapper.selectAvailableUserByUserName(userName);
    }

    @Override
    public UserPO selectAvailableUserByUserId(Long userId) {
        return userMapper.selectAvailableUserByUserId(userId);
    }
}
