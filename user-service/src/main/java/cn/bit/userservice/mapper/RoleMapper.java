package cn.bit.userservice.mapper;

import cn.bit.core.pojo.po.user.RoleDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface RoleMapper extends BaseMapper<RoleDictItem> {
    Long selectRoleIdByCode(String roleCode);
}
