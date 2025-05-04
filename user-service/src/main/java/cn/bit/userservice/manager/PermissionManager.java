package cn.bit.userservice.manager;

import cn.bit.core.pojo.dto.BitGoAuthorization;
import cn.bit.core.pojo.po.user.PermissionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface PermissionManager extends IService<PermissionPO> {
    Long insert(PermissionPO permissionPO);
    Set<BitGoAuthorization> selectAvailableBitGoAuthorizationByUserId(Long userId);
}
