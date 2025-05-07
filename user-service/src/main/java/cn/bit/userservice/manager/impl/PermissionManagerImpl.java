package cn.bit.userservice.manager.impl;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.po.user.PermissionPO;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.userservice.manager.PermissionManager;
import cn.bit.userservice.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class PermissionManagerImpl extends ServiceImpl<PermissionMapper, PermissionPO> implements PermissionManager {
    private final PermissionMapper permissionMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;
    @Override
    public Long insertPermission(PermissionPO permissionPO) {
        Long id = idGenerator.nextId();
        permissionPO.setPermissionId(id);
        save(permissionPO);
        return id;
    }

    @Override
    public Set<BitGoAuthorization> selectAvailableBitGoAuthorizationsByUserId(Long userId) {
        return permissionMapper.selectAvailableBitGoAuthorizationsByUserId(userId);
    }

}
