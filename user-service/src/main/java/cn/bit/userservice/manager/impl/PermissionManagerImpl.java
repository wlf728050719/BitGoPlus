package cn.bit.userservice.manager.impl;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.po.user.PermissionPO;
import cn.bit.userservice.manager.PermissionManager;
import cn.bit.userservice.mapper.PermissionMapper;
import cn.bit.userservice.mapper.RoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class PermissionManagerImpl extends ServiceImpl<PermissionMapper, PermissionPO> implements PermissionManager {
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;

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
        Long roleId = roleMapper.selectRoleIdByCode(roleCode);
        QueryWrapper<PermissionPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("role_id", roleId).eq("del_flag", 0);
        PermissionPO permissionPO = permissionMapper.selectOne(queryWrapper);
        permissionPO.setTenantId(tenantId);
        return permissionMapper.updateById(permissionPO) == 1;
    }
}
