package cn.bit.authservice.service;

import cn.bit.core.pojo.vo.R;

import java.util.Map;

public interface AuthenticationService {
    R<Map<String, String>> createAuthenticationToken(String username, String password);

    R<String> refreshToken(String refreshToken);
}
