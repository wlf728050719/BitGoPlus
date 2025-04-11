package cn.bit.userservice.controller;

import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.po.RoleDictItem;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import cn.bit.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.pojo.po.UserPO;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Validated
public class UserController {
    private UserService userService;

    @GetMapping("/test/{data}")
    @PreAuthorize("hasRole('admin')")
    public R<String> test(@PathVariable String data) {
        System.out.println(SecurityUtils.getUser().getUsername());
        return R.ok(data, "user-service ok");
    }

    @PostMapping("/register")
    public R<Boolean> register(@RequestParam String code, @RequestParam String roleCode, @RequestBody @Valid UserBaseInfo userBaseInfo) {
        return userService.register(code, roleCode, userBaseInfo);
    }

    @GetMapping("/infoByUsername/{username}")
    public R<UserPO> infoByUsername(@PathVariable @ValidString(StringEnum.USERNAME_STRING) String username) {
        return userService.infoByUsername(username);
    }

    @GetMapping("/infoByUserId/{userId}")
    public R<UserPO> infoByUserId(@PathVariable Long userId) {
        return userService.infoByUserId(userId);
    }

    @GetMapping("/rolesByUserId/{userId}")
    public R<Set<RoleDictItem>> rolesByUserId(@PathVariable Long userId) {
        return userService.rolesByUserId(userId);
    }

}
