package cn.bit.userservice.service.impl;

import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.pojo.po.UserPO;
import cn.bit.userservice.mapper.UserMapper;
import cn.bit.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
