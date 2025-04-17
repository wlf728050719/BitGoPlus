package cn.bit.userservice.service.impl;

import cn.bit.constant.RedisExpire;
import cn.bit.constant.RedisKey;
import cn.bit.exception.BizException;
import cn.bit.mail.BitGoMailSender;
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
import cn.bit.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserManager userManager;
    private final RoleManager roleManager;
    private final PermissionManager permissionManager;
    private final BitGoMailSender bitGoMailSender;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> register(String code, String roleCode, UserBaseInfo userBaseInfo) {
        // 判断用户注册角色是否合法
        List<RoleDictItem> roleDictItemList = roleManager.getRoleDict();
        RoleDictItem role =
            roleDictItemList.stream().filter(roleDictItem -> roleDictItem.getRoleCode().equals(roleCode)).findFirst()
                .orElseThrow(() -> new BizException("不存在对应角色"));
        // 判断用户名是否存在
        if (userManager.selectUserByUserName(userBaseInfo.getUsername()) != null) {
            return R.failed(false, "存在同名用户");
        }
        // 判断验证码是否存在
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, userBaseInfo.getEmail());
        String temp = (String) redisTemplate.opsForValue().get(key);
        log.error(temp);
        if (Objects.isNull(temp) || !temp.equals(code)) {
            return R.failed(false, "验证码超时或错误,请重新发送");
        }
        redisTemplate.delete(key);
        // 注册
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
    public R<Boolean> sendRegisterCodeByMail(String email) {
        String key = String.format(RedisKey.CODE_REGISTER_MAIL_KEY_FORMAT, email);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            throw new BizException("当前发送验证码过于频繁，请等待" + RedisExpire.REGISTER_CODE_EXPIRE_SECONDS + "秒");
        } else {
            String code = RandomUtil.getRandomNumberString(6);
            bitGoMailSender.send(email, "BitGo", "您正在注册BitGo商城账号，验证码为: " + code + " 。");
            redisTemplate.opsForValue().set(key, code, RedisExpire.REGISTER_CODE_EXPIRE_SECONDS, TimeUnit.SECONDS);
        }
        return R.ok(true, "验证码发送成功，请及时查收邮件");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUsername(String username) {
        return R.ok(new UserBaseInfo(userManager.selectUserByUserName(username)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<UserBaseInfo> getInfoByUserId(Long userId) {
        return R.ok(new UserBaseInfo(userManager.selectUserByUserId(userId)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(Long userId) {
        return R.ok(permissionManager.selectBitGoAuthorizationByUserId(userId));
    }
}
