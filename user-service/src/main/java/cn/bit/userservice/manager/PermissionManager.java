package cn.bit.userservice.manager;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.po.user.PermissionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface PermissionManager extends IService<PermissionPO> {
    Long insertPermission(PermissionPO permissionPO);
    Set<BitGoAuthorization> selectAvailableBitGoAuthorizationsByUserId(Long userId);
}
