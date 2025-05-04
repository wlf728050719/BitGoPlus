package cn.bit.userservice.manager;

import cn.bit.core.pojo.po.user.RoleDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface RoleManager extends IService<RoleDictItem> {

    Set<RoleDictItem> getRoleDict();
}
