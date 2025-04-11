package cn.bit.userservice.manager;

import cn.bit.pojo.po.RoleDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleManager extends IService<RoleDictItem> {

    List<RoleDictItem> getRoleDict();
}
