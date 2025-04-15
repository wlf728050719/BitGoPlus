package cn.bit.userservice.manager;

import cn.bit.pojo.po.user.PermissionPO;
import cn.bit.pojo.po.user.RoleDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface PermissionManager extends IService<PermissionPO> {
    Set<RoleDictItem> selectPermissionByUserId(Long userId);
}
