package cn.bit.userservice.mapper;

import cn.bit.pojo.po.user.PermissionPO;
import cn.bit.pojo.po.user.RoleDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

public interface PermissionMapper extends BaseMapper<PermissionPO> {
    Set<RoleDictItem> selectPermissionByUserId(Long userId);
}
