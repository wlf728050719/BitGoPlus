package cn.bit.userservice.mapper;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.po.user.PermissionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

public interface PermissionMapper extends BaseMapper<PermissionPO> {
    Set<BitGoAuthorization> selectBitGoAuthorizationByUserId(Long userId);

    Set<String> selectUndeletedUserRoleCodeByVerifiedEmail(String email);

    boolean setUserTenantIdByUserIdAndRoleCode(Long userId, Long tenantId, String roleCode);
}
