package cn.bit.authservice.controller;

import java.util.HashMap;
import java.util.Map;

import cn.bit.service.BitGoUserService;
import cn.bit.constant.SecurityConstant;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.vo.R;
import cn.bit.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final JwtUtil jwtUtil;
    private final BitGoUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public R<Map<String, String>> createAuthenticationToken(@RequestParam String username,
        @RequestParam String password) {
        // 生成 Access Token 和 Refresh Token
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return R.failed("密码错误");
        }
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        Map<String, String> result = new HashMap<>();
        result.put(SecurityConstant.ACCESS_TOKEN, accessToken);
        result.put(SecurityConstant.REFRESH_TOKEN, refreshToken);
        return R.ok(result);
    }

    @PostMapping("/refresh")
    public R<String> refreshToken(@RequestHeader(SecurityConstant.AUTHORIZATION_HEADER) String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        BitGoUser user = (BitGoUser) userService.loadUserByUsername(username);
        if (jwtUtil.validateToken(refreshToken, user)) {
            return R.ok(jwtUtil.generateAccessToken(user));
        } else {
            return R.failed("用户登录状态已过期,请重新登录");
        }
    }
}
