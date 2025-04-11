package cn.bit.userservice.service.impl;

import cn.bit.exception.BizException;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.po.PermissionPO;
import cn.bit.pojo.po.RoleDictItem;
import cn.bit.pojo.po.UserPO;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.manager.PermissionManager;
import cn.bit.userservice.manager.RoleManager;
import cn.bit.userservice.manager.UserManager;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserManager userManager;
    private final RoleManager roleManager;
    private final PermissionManager permissionManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> register(String code, String roleCode, UserBaseInfo userBaseInfo) {
        // 判断code是否存在
        // 判断用户注册角色是否合法
        List<RoleDictItem> roleDictItemList = roleManager.getRoleDict();
        RoleDictItem role =
            roleDictItemList.stream().filter(roleDictItem -> roleDictItem.getRoleCode().equals(roleCode)).findFirst()
                .orElseThrow(() -> new BizException("不存在对应角色"));
        // 判断用户名是否存在
        if (userManager.selectUserByUserName(userBaseInfo.getUsername()) != null) {
            return R.failed(false, "存在同名用户");
        }
        UserPO userPO = userBaseInfo.newUserPO();
        userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        Long userId = userManager.insert(userPO);
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setUserId(userId);
        permissionPO.setRoleId(role.getRoleId());
        permissionManager.save(permissionPO);
        return R.ok(true, "注册成功");
    }

    @Override
    public R<UserPO> infoByUsername(String username) {
        return R.ok(userManager.selectUserByUserName(username));
    }

    @Override
    public R<UserPO> infoByUserId(Long userId) {
        return R.ok(userManager.selectUserByUserId(userId));
    }

    @Override
    public R<Set<RoleDictItem>> rolesByUserId(Long userId) {
        return R.ok(permissionManager.selectPermissionByUserId(userId));
    }
}
