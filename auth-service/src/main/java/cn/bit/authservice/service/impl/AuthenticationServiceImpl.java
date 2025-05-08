package cn.bit.authservice.service.impl;

import cn.bit.authservice.service.AuthenticationService;
import cn.bit.core.constant.RedisExpire;
import cn.bit.core.constant.RedisKey;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.core.pojo.dto.security.BitGoUser;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.core.pojo.vo.R;
import cn.bit.feign.client.UserClient;
import cn.bit.message.util.CodeUtil;
import cn.bit.security.service.BitGoUserService;
import cn.bit.security.util.JwtUtil;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserClient userClient;
    private final CodeUtil codeUtil;
    private final JwtUtil jwtUtil;
    private final BitGoUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 使用用户名+密码获取token
     * 
     * @param username 用户名
     * @param password 密码
     * @return {access_token:xx,refresh_token,xx}
     * @throws BizException 短时多次密码错误业务异常
     */
    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public Map<String, String> loginByUsernameAndPassword(String username, String password) throws BizException {
        // 构建密码错误次数的Redis key
        String passwordErrorKey = String.format(RedisKey.PASSWORD_ERROR_COUNT_KEY_FORMAT, username);
        // 获取当前错误次数（如果key不存在则返回0）
        String errorCountStr = (String) redisTemplate.opsForValue().get(passwordErrorKey);
        int errorCount = errorCountStr != null ? Integer.parseInt(errorCountStr) : 0;
        // 初始化超时时间
        Long expireTime = RedisExpire.PASSWORD_ERROR_EXPIRE_SECONDS;
        // 判断错误次数是否超过上限
        if (errorCount >= SecurityConstant.MAX_PASSWORD_ERROR_COUNT) {
            // 获取key的剩余时间
            String expireMessage = null;
            expireTime = redisTemplate.getExpire(passwordErrorKey, TimeUnit.SECONDS);
            if (expireTime != null && expireTime >= 60) {
                expireMessage = expireTime / 60 + "分钟后再试";
            } else if (expireTime != null) {
                expireMessage = expireTime + "秒后再试";
            }
            throw new BizException("密码错误次数过多，请" + expireMessage);
        }

        // 获取用户信息
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        // 验证密码
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String newErrorCountStr = String.valueOf(errorCount + 1);
            // 设置错误次数，并设置过期时间（如果key不存在）
            redisTemplate.opsForValue().set(passwordErrorKey, newErrorCountStr, expireTime, TimeUnit.SECONDS);
            throw new BizException("密码错误，剩余尝试次数: " + (SecurityConstant.MAX_PASSWORD_ERROR_COUNT - errorCount - 1));
        }
        // 密码正确，清除错误计数（如果存在）
        if (errorCount > 0) {
            redisTemplate.delete(passwordErrorKey);
        }
        return deliverUserToken(username);
    }

    /**
     * 发送邮件登录验证码
     * 
     * @param email 邮箱
     * @return true
     */
    @Override
    public Boolean sendLoginCodeByEmail(String email) {
        String lock = String.format(RedisKey.CODE_MAIL_LOCK, email);
        String key = String.format(RedisKey.CODE_LOGIN_MAIL_KEY_FORMAT, email);
        codeUtil.sendMailCode(lock, key, email, RedisExpire.LOGIN_CODE_EXPIRE_SECONDS, "您正在登录BitGo商城用户！");
        return true;
    }

    /**
     * 使用邮箱和验证码登录
     * @param email 邮箱
     * @param code 验证码
     * @return {access_token:xx,refresh_token,xx}
     */
    @Override
    public Map<String, String> loginByEmailAndCode(String email, String code) {
        String key = String.format(RedisKey.CODE_LOGIN_MAIL_KEY_FORMAT, email);
        codeUtil.checkMailCode(key, code);
        R<UserBaseInfo> response = userClient.getUserBaseInfoByVerifiedEmail(email);
        if (response == null) {
            throw new SysException("get response from user-service failed");
        }
        UserBaseInfo userBaseInfo = response.getData();
        if (userBaseInfo == null) {
            throw new BizException("不存在对应邮箱用户或邮箱未被验证，请使用其他方式登录");
        }
        if (userBaseInfo.getLockFlag() == 1) {
            throw new BizException("用户已被冻结，请联系管理员解封");
        }
        return deliverUserToken(userBaseInfo.getUsername());
    }

    /**
     * 使用refreshToken获取新的accessToken
     * 
     * @param refreshToken refreshToken
     * @return accessToken
     */
    @Override
    public String refreshToken(String refreshToken) {
        String username = jwtUtil.extractData(refreshToken);
        // 判断是否存在对应用户
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        return jwtUtil.generateAccessToken(user.getUsername());
    }

    /**
     * 分发token(生成accessToken和refreshToken并缓存accessToken)
     * @param username 用户名
     * @return {access_token:xx,refresh_token,xx}
     */
    private Map<String, String> deliverUserToken(String username) {
        // 生成token
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);
        String tokenKey = String.format(RedisKey.TOKEN_KEY_FORMAT, username);
        // 存储token到Redis
        redisTemplate.opsForValue().set(tokenKey, accessToken, jwtUtil.getAccessTokenExpiration(), TimeUnit.SECONDS);
        Map<String, String> result = new HashMap<>();
        result.put(SecurityConstant.ACCESS_TOKEN, accessToken);
        result.put(SecurityConstant.REFRESH_TOKEN, refreshToken);
        return result;
    }
}
