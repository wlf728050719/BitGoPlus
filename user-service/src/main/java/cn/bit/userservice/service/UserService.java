package cn.bit.userservice.service;

import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.po.user.RoleDictItem;
import cn.bit.pojo.po.user.UserPO;
import cn.bit.pojo.vo.R;

import java.util.Set;

public interface UserService {
    R<Boolean> register(String code, String roleCode, UserBaseInfo user);

    R<UserPO> infoByUsername(String username);

    R<UserPO> infoByUserId(Long userId);

    R<Set<RoleDictItem>> rolesByUserId(Long userId);
}
