package cn.bit.authservice.controller;

import java.util.Map;

import cn.bit.authservice.service.AuthenticationService;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.jsr303.annotation.ValidString;
import cn.bit.core.jsr303.enums.StringEnum;
import cn.bit.core.pojo.vo.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login/usernameAndPassword")
    public R<Map<String, String>> loginByUsernameAndPassword(@RequestParam String username,
        @RequestParam @Valid @ValidString(StringEnum.PASSWORD_STRING) String password) {
        return R.ok(authenticationService.loginByUsernameAndPassword(username, password));
    }

    @PostMapping("/refresh")
    public R<String> refreshToken(@RequestHeader(SecurityConstant.HEADER_AUTHORIZATION) String refreshToken) {
        return R.ok(authenticationService.refreshToken(refreshToken));
    }

    @PostMapping("/login/sendCode/email")
    public R<Boolean> sendLoginCodeByEmail(@RequestParam @Valid @ValidString(StringEnum.EMAIL_STRING) String email) {
        return R.ok(authenticationService.sendLoginCodeByEmail(email), "验证码发送成功");
    }

    @PostMapping("/login/emailAndCode")
    public R<Map<String, String>> loginByEmailAndCode(@RequestParam @Valid @NotNull String code,
        @RequestParam @Valid @ValidString(StringEnum.EMAIL_STRING) String email) {
        return R.ok(authenticationService.loginByEmailAndCode(email, code));
    }

}
