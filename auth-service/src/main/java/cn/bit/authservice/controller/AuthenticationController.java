package cn.bit.authservice.controller;

import java.util.Map;

import cn.bit.authservice.service.AuthenticationService;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.vo.R;
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
    private AuthenticationService authenticationService;

    @SuppressWarnings("checkstyle:ReturnCount")
    @PostMapping("/login")
    public R<Map<String, String>> createAuthenticationToken(@RequestParam String username,
        @RequestParam String password) {
       return authenticationService.createAuthenticationToken(username, password);
    }

    @PostMapping("/refresh")
    public R<String> refreshToken(@RequestHeader(SecurityConstant.HEADER_AUTHORIZATION) String refreshToken) {
       return authenticationService.refreshToken(refreshToken);
    }
}
