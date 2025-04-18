package cn.bit.userservice.service.impl;

import cn.bit.constant.RedisExpire;
import cn.bit.constant.RedisKey;
import cn.bit.exception.BizException;
import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.po.user.PermissionPO;
import cn.bit.pojo.po.user.RoleDictItem;
import cn.bit.pojo.po.user.UserPO;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.manager.PermissionManager;
import cn.bit.userservice.manager.RoleManager;
import cn.bit.userservice.manager.UserManager;
import cn.bit.userservice.service.UserService;
import cn.bit.util.CodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CodeUtil codeUtil;
    private final UserManager userManager;
    private final RoleManager roleManager;
    private final PermissionManager permissionManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> registerByEmail(String code, String roleCode, UserBaseInfo userBaseInfo) {
        // 判断用户注册角色是否合法
        Set<RoleDictItem> roleDictItemSet = roleManager.getRoleDict();
        RoleDictItem role = roleDictItemSet.stream().filter(roleDictItem -> roleDictItem.getRoleCode().equals(roleCode))
            .findFirst().orElseThrow(() -> new BizException("不存在对应角色"));
        // 判断用户名是否存在
        if (userManager.selectUserByUserName(userBaseInfo.getUsername()) != null) {
            return R.failed(false, "存在同名用户");
        }
        // 判断该邮箱下是否已有对应角色
        Set<String> roleCodes = permissionManager.selectUndeletedUserRoleCodeByVerifiedEmail(userBaseInfo.getEmail());
        if (roleCodes.contains(roleCode)) {
            return R.failed(false, "该邮箱已有对应角色用户");
        }
        // 判断验证码是否存在
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, userBaseInfo.getEmail());
        codeUtil.checkMailCode(key, code);
        // 注册
        UserPO userPO = userBaseInfo.newUserPO();
        userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        userPO.setEmailVerify(1);
        Long userId = userManager.insert(userPO);
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setUserId(userId);
        permissionPO.setRoleId(role.getRoleId());
        permissionManager.save(permissionPO);
        return R.ok(true, "注册成功");
    }

    @Override
    public R<Boolean> changePasswordByMail(String username, String code, String email, String password) {
        // 判断验证码是否存在
        String key = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.checkMailCode(key, code);
        UserPO user = userManager.selectUserByUserName(username);
        if (user == null) {
            return R.failed(false, "不存在对应用户名用户");
        }
        if (!Objects.equals(user.getEmail(), email) || user.getEmailVerify() != 1) {
            return R.failed(false, "该用户邮箱未认证，请使用其他方式修改密码");
        }
        userManager.updatePasswordByAvailableUsername(username, bCryptPasswordEncoder.encode(password));
        return R.ok(true, "密码修改成功");
    }

    @Override
    public R<Boolean> sendRegisterCodeByEmail(String email) {
        String lock = String.format(RedisKey.CODE_REGISTER_MAIL_LOCK_FORMAT, email);
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.REGISTER_CODE_EXPIRE_SECONDS, "您正在注册BitGo商城用户！");
        return R.ok(true, "(注册)验证码发送成功，请及时查收邮件");
    }

    @Override
    public R<Boolean> sendChangePasswordCodeByMail(String email) {
        String lock = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_LOCK_FORMAT, email);
        String key = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.CHANGE_PASSWORD_CODE_EXPIRE_SECONDS, "您正在修改BitGo商城用户密码！");
        return R.ok(true, "(修改密码)验证码发送成功，请及时查收邮件");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUsername(String username) {
        UserPO user = userManager.selectUserByUserName(username);
        if (user == null) {
            return R.failed(null, "不存在对应用户名用户");
        } else {
            return R.ok(new UserBaseInfo(user));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUserId(Long userId) {
        UserPO user = userManager.selectUserByUserId(userId);
        if (user == null) {
            return R.failed(null, "不存在对应用户ID用户");
        } else {
            return R.ok(new UserBaseInfo(user));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(Long userId) {
        return R.ok(permissionManager.selectBitGoAuthorizationByUserId(userId));
    }
}
