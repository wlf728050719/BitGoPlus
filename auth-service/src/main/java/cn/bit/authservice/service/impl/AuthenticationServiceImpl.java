package cn.bit.authservice.service.impl;

import cn.bit.authservice.service.AuthenticationService;
import cn.bit.constant.RedisKey;
import cn.bit.constant.SecurityConstant;
import cn.bit.exception.BizException;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.vo.R;
import cn.bit.service.BitGoUserService;
import cn.bit.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final BitGoUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public R<Map<String, String>> createAuthenticationToken(String username, String password) {
        // 生成 Access Token 和 Refresh Token
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BizException("密码错误");
        }
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        String key = String.format(RedisKey.TOKEN_KEY_FORMAT, user.getUsername());
        redisTemplate.opsForValue().set(key, accessToken);
        Map<String, String> result = new HashMap<>();
        result.put(SecurityConstant.ACCESS_TOKEN, accessToken);
        result.put(SecurityConstant.REFRESH_TOKEN, refreshToken);
        return R.ok(result);
    }

    @Override
    public R<String> refreshToken(String refreshToken) {
        String username = jwtUtil.extractData(refreshToken);
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        if (jwtUtil.validateToken(refreshToken, user)) {
            return R.ok(jwtUtil.generateAccessToken(user));
        } else {
            throw new BizException("用户登录状态已过期,请重新登录");
        }
    }
}
