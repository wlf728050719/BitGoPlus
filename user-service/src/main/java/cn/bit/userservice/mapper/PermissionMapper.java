package cn.bit.userservice.mapper;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.po.user.PermissionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

public interface PermissionMapper extends BaseMapper<PermissionPO> {
    Set<BitGoAuthorization> selectAvailableBitGoAuthorizationByUserId(Long userId);
}
