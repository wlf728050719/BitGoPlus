package cn.bit.userservice.manager.impl;

import cn.bit.pojo.po.user.PermissionPO;
import cn.bit.pojo.po.user.RoleDictItem;
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
    @Override
    public Set<RoleDictItem> selectPermissionByUserId(Long userId) {
        return permissionMapper.selectPermissionByUserId(userId);
    }
}
