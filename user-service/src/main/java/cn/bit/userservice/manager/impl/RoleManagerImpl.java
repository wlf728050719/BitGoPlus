package cn.bit.userservice.manager.impl;

import cn.bit.pojo.po.RoleDictItem;
import cn.bit.redis.constant.RedisKey;
import cn.bit.userservice.manager.RoleManager;
import cn.bit.userservice.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleManagerImpl extends ServiceImpl<RoleMapper, RoleDictItem> implements RoleManager {
    @Override
    @Cacheable(value = RedisKey.NAMESPACE, keyGenerator = "DictCacheGenerator")
    public List<RoleDictItem> getRoleDict() {
        return this.list();
    }
}
