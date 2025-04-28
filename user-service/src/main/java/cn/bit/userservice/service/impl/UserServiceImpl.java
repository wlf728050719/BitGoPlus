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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CodeUtil codeUtil;
    private final UserManager userManager;
    private final RoleManager roleManager;
    private final PermissionManager permissionManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> registerByEmail(String code, String roleCode, UserBaseInfo userBaseInfo) {
        // 判断用户注册角色是否合法
        Set<RoleDictItem> roleDictItemSet = roleManager.getRoleDict();
        RoleDictItem role = roleDictItemSet.stream().filter(roleDictItem -> roleDictItem.getRoleCode().equals(roleCode))
            .findFirst().orElseThrow(() -> new BizException("不存在对应角色"));
        // 判断用户名是否存在
        if (userManager.selectUserByUserName(userBaseInfo.getUsername()) != null) {
            throw new BizException("存在同名用户");
        }
        // 判断该邮箱下是否已有对应角色
        Set<String> roleCodes = permissionManager.selectUndeletedUserRoleCodeByVerifiedEmail(userBaseInfo.getEmail());
        if (roleCodes.contains(roleCode)) {
            throw new BizException("该邮箱已有对应角色用户");
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
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> changePasswordByMail(String code, String email, String username, String password) {
        // 判断验证码是否存在
        String codeKey = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.checkMailCode(codeKey, code);
        // 检查用户是否可用
        UserPO user = userManager.selectUserByUserName(username);
        if (user == null) {
            throw new BizException("不存在对应用户名用户,检查用户名是否正确");
        }
        if (user.getDelFlag() == 1) {
            throw new BizException("用户已被注销");
        }
        if (user.getLockFlag() == 1) {
            throw new BizException("用户已被冻结，请联系管理员解封");
        }
        // 检查用户邮箱是否验证
        if (!Objects.equals(user.getEmail(), email) || user.getEmailVerify() != 1) {
            throw new BizException("该用户邮箱未认证，请使用其他方式修改密码");
        }
        // 修改密码
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userManager.updateById(user);
        // 删除缓存token
        String tokenKey = String.format(RedisKey.TOKEN_KEY_FORMAT, user.getUsername());
        redisTemplate.delete(tokenKey);
        return R.ok(true, "密码修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<List<UserBaseInfo>> getUserBaseInfosByEmail(String code, String email) {
        // 判断验证码是否存在
        String codeKey = String.format(RedisKey.CODE_GET_USER_BASE_INFO_MAIL_KEY_FORMAT, email);
        codeUtil.checkMailCode(codeKey, code);
        List<UserBaseInfo> userBaseInfos = Optional.ofNullable(userManager.selectUsersByVerifiedEmail(email))
            .orElseGet(Collections::emptyList).stream().filter(Objects::nonNull) // 过滤掉可能为null的UserPO
            .map(UserBaseInfo::new).collect(Collectors.toList());
        return R.ok(userBaseInfos);
    }

    @Override
    public R<Boolean> sendRegisterCodeByEmail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.REGISTER_CODE_EXPIRE_SECONDS, "您正在注册BitGo商城用户！");
        return R.ok(true, "(注册)验证码发送成功，请及时查收邮件");
    }

    @Override
    public R<Boolean> sendChangePasswordCodeByMail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.CHANGE_PASSWORD_CODE_EXPIRE_SECONDS, "您正在修改BitGo商城用户密码！");
        return R.ok(true, "(修改密码)验证码发送成功，请及时查收邮件");
    }

    @Override
    public R<Boolean> sendGetUserBaseInfoCodeByEmail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_GET_USER_BASE_INFO_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.GET_USER_BASE_INFO_CODE_EXPIRE_SECONDS,
            "您正在获取BitGo商城注册用户名信息！");
        return R.ok(true, "(注册用户名获取)验证码发送成功，请及时查收邮件");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUsername(String username) {
        UserPO user = userManager.selectUserByUserName(username);
        if (user == null) {
            throw new BizException("不存在对应用户名用户");
        } else {
            return R.ok(new UserBaseInfo(user));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUserId(Long userId) {
        UserPO user = userManager.selectUserByUserId(userId);
        if (user == null) {
            throw new BizException("不存在对应用户ID用户");
        } else {
            return R.ok(new UserBaseInfo(user));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(Long userId) {
        return R.ok(permissionManager.selectBitGoAuthorizationByUserId(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> setUserTenantIdByUserIdAndRoleCode(Long userId, Long tenantId, String roleCode) {
        return R.ok(permissionManager.setUserTenantIdByUserIdAndRoleCode(userId, tenantId, roleCode));
    }

}
