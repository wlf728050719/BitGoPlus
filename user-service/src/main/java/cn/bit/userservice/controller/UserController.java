package cn.bit.userservice.controller;

import cn.bit.annotation.Admin;
import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import cn.bit.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Validated
public class UserController {
    private UserService userService;

    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user = (BitGoUser) SecurityUtils.getUser();
        return R.ok(data,
            "user-service ok,username: " + user.getUsername() + "userId: " + user.getUserBaseInfo().getUserId());
    }

    @PostMapping("/open/register/email")
    public R<Boolean> registerByEmail(@RequestParam @Valid @NotNull String code,
        @RequestBody @Valid UserBaseInfo userBaseInfo) {
        return R.ok(userService.registerByEmail(code, userBaseInfo), "注册成功");
    }

    @PostMapping("/open/register/sendCode/email")
    public R<Boolean> sendRegisterCodeByEmail(@RequestParam @Valid @ValidString(StringEnum.EMAIL_STRING) String email) {
        return R.ok(userService.sendRegisterCodeByEmail(email), "邮件发送成功,请及时查收");
    }

    @PutMapping("/open/changePwd/email")
    public R<Boolean> changePasswordByEmail(@RequestParam @Valid @NotNull String code,
        @RequestParam @Valid @ValidString(StringEnum.EMAIL_STRING) String email,
        @RequestParam @Valid @ValidString(StringEnum.PASSWORD_STRING) String password) {
        return R.ok(userService.changePasswordByMail(code, email, password), "密码修改成功");
    }

    @PostMapping("/open/changePwd/sendCode/email")
    public R<Boolean>
        sendChangePasswordCodeByEmail(@RequestParam @Valid @ValidString(StringEnum.EMAIL_STRING) String email) {
        return R.ok(userService.sendChangePasswordCodeByMail(email), "邮件发送成功,请及时查收");
    }
}
