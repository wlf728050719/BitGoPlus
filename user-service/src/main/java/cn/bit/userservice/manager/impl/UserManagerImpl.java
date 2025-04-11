package cn.bit.userservice.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.bit.pojo.po.UserPO;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.manager.UserManager;
import lombok.AllArgsConstructor;

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
    public UserPO selectUserByUserName(String userName) {
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserPO selectUserByUserId(Long userId) {
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userMapper.selectOne(queryWrapper);
    }
}
