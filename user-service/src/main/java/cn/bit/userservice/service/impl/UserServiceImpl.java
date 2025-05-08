package cn.bit.userservice.service.impl;

import cn.bit.core.constant.RedisExpire;
import cn.bit.core.constant.RedisKey;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.core.pojo.po.user.PermissionPO;
import cn.bit.core.pojo.po.user.RoleDictItem;
import cn.bit.core.pojo.po.user.UserPO;
import cn.bit.userservice.manager.PermissionManager;
import cn.bit.userservice.manager.RoleManager;
import cn.bit.userservice.manager.UserManager;
import cn.bit.userservice.service.UserService;
import cn.bit.message.util.CodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>用户服务</p>
 * Date:2025/04/29 20:20:51
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CodeUtil codeUtil;
    private final RoleManager roleManager;
    private final UserManager userManager;
    private final PermissionManager permissionManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 通过邮件注册账户
     *
     * @param code         验证码
     * @param userBaseInfo 用户基本信息
     * @return {@link Boolean }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerByEmail(String code, UserBaseInfo userBaseInfo) {
        // 判断用户名是否存在
        if (userManager.selectUserPOsByUserName(userBaseInfo.getUsername()) != null) {
            throw new BizException("存在同名用户");
        }
        // 判断邮箱是否被验证过
        if (userManager.selectUndeletedUserPOByVerifiedEmail(userBaseInfo.getEmail()) != null) {
            throw new BizException("邮箱已经认证，请使用其他邮箱");
        }
        // 判断验证码是否存在
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, userBaseInfo.getEmail());
        codeUtil.checkMailCode(key, code);
        // 注册
        UserPO userPO = userBaseInfo.newUserPO();
        userPO.setPassword(bCryptPasswordEncoder.encode(userPO.getPassword()));
        userPO.setEmailVerify(1);
        Long userId = userManager.insertUser(userPO);
        // 默认添加customer权限
        addPermission(SecurityConstant.ROLE_CUSTOMER, userId, userId);
        return true;
    }

    /**
     * 用户新增权限
     *
     * @param roleCode 角色码
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return {@link Boolean }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addPermission(String roleCode, Long tenantId, Long userId) {
        // 判断是否存在对应角色
        Set<RoleDictItem> roleDictItemSet = roleManager.selectRoleDict();
        RoleDictItem roleDictItem = roleDictItemSet.stream().filter(item -> roleCode.equals(item.getRoleCode()))
                .findFirst().orElseThrow(() -> new SysException("role not found: " + roleCode));
        // 判断用户状态是否可用
        UserPO userPO = userManager.selectUserPOByUserId(userId);
        if (userPO == null) {
            throw new SysException("user not found: " + userId);
        }
        if (userPO.getDelFlag() == 1) {
            throw new BizException("用户已被注销");
        }
        if (userPO.getLockFlag() == 1) {
            throw new BizException("用户已被冻结，请联系管理员解封");
        }
        // 判断用户是否已有对应权限
        Set<BitGoAuthorization> authorizationSet = permissionManager.selectAvailableBitGoAuthorizationsByUserId(userId);
        BitGoAuthorization bitGoAuthorization = authorizationSet.stream()
                .filter(item -> roleCode.equals(item.getRoleCode()) && tenantId.equals(item.getTenantId())).findFirst()
                .orElse(null);
        if (bitGoAuthorization != null) {
            throw new BizException("用户已有对应租户角色权限");
        }
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setRoleId(roleDictItem.getRoleId());
        permissionPO.setTenantId(tenantId);
        permissionPO.setUserId(userId);
        permissionManager.insertPermission(permissionPO);
        return true;
    }

    /**
     * 邮箱+验证码修改密码
     *
     * @param code     验证码
     * @param email    邮箱
     * @param password 密码
     * @return {@link Boolean }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePasswordByMail(String code, String email, String password) {
        // 判断验证码是否存在
        String codeKey = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.checkMailCode(codeKey, code);
        // 检查用户是否可用
        UserPO user = userManager.selectUndeletedUserPOByVerifiedEmail(email);
        if (user == null) {
            throw new BizException("不存在使用该邮箱认证的账号");
        }
        if (user.getLockFlag() == 1) {
            throw new BizException("用户已被冻结，请联系管理员解封");
        }
        // 修改密码
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userManager.updateById(user);
        // 删除缓存token
        String tokenKey = String.format(RedisKey.TOKEN_KEY_FORMAT, user.getUsername());
        redisTemplate.delete(tokenKey);
        return true;
    }

    /**
     * 邮件发送注册验证码
     *
     * @param email 邮箱
     * @return {@link Boolean }
     */
    @Override
    public Boolean sendRegisterCodeByEmail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.REGISTER_CODE_EXPIRE_SECONDS, "您正在注册BitGo商城用户！");
        return true;
    }

    /**
     * 邮件发送修改密码验证码
     *
     * @param email 邮箱
     * @return {@link Boolean }
     */
    @Override
    public Boolean sendChangePasswordCodeByMail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.CHANGE_PASSWORD_CODE_EXPIRE_SECONDS, "您正在修改BitGo商城用户密码！");
        return true;
    }

    /**
     * 根据用户名获取对应用户基本信息列表(未脱敏+已删除)
     * @param username 用户名
     * @return 用户基本信息列表
     */
    @Override
    public List<UserBaseInfo> getUserBaseInfosByUsername(String username) {
        List<UserPO> userPOList = userManager.selectUserPOsByUserName(username);
        return userPOList.stream().map(UserBaseInfo::new).collect(Collectors.toList());
    }

    /**
     * 通过用户ID获取用户基本信息(未脱敏+已删除)
     *
     * @param userId 用户ID
     * @return {@link UserBaseInfo }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserBaseInfo getUserBaseInfoByUserId(Long userId) {
        UserPO user = userManager.selectUserPOByUserId(userId);
        return new UserBaseInfo(user);
    }

    /**
     * 根据邮箱获取对应已验证邮箱用户基本信息(未脱敏+未删除)
     * @param email 邮箱
     * @return 用户基本信息
     */
    @Override
    public UserBaseInfo getUserBaseInfoByVerifiedEmail(String email) {
        UserPO userPO = userManager.selectUndeletedUserPOByVerifiedEmail(email);
        return new UserBaseInfo(userPO);
    }

    /**
     * 通过用户ID获取可用的用户权限(未删除)
     * @param userId 用户ID
     * @return {@link Set }<{@link BitGoAuthorization }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<BitGoAuthorization> getAvailableBitGoAuthorizationsByUserId(Long userId) {
        return permissionManager.selectAvailableBitGoAuthorizationsByUserId(userId);
    }
}
