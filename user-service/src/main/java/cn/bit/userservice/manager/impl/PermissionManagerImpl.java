package cn.bit.userservice.manager.impl;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.po.user.PermissionPO;
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
    public Set<BitGoAuthorization> selectBitGoAuthorizationByUserId(Long userId) {
        return permissionMapper.selectBitGoAuthorizationByUserId(userId);
    }

    @Override
    public Set<String> selectUndeletedUserRoleCodeByVerifiedEmail(String email) {
        return permissionMapper.selectUndeletedUserRoleCodeByVerifiedEmail(email);
    }

    @Override
    public boolean setUserTenantIdByUserIdAndRoleCode(Long userId, Long tenantId, String roleCode) {
        return permissionMapper.setUserTenantIdByUserIdAndRoleCode(userId, tenantId, roleCode);
    }
}
