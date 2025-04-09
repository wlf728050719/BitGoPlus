package cn.bit.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.bit.pojo.po.UserPO;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    private final UserMapper userMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public int register(UserPO userPO) {
        userPO.setUserId(idGenerator.nextId());
        return userMapper.insert(userPO);
    }

    @Override
    public long count() {
        return userMapper.selectCount(new LambdaQueryWrapper<UserPO>());
    }
}
