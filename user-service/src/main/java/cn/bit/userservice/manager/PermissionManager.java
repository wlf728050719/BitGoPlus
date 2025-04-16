package cn.bit.userservice.manager;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.po.user.PermissionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface PermissionManager extends IService<PermissionPO> {
    Set<BitGoAuthorization> selectBitGoAuthorizationByUserId(Long userId);
}
