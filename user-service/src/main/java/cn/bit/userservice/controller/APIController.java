package cn.bit.userservice.controller;

import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.pojo.po.RoleDictItem;
import cn.bit.pojo.po.UserPO;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class APIController {
    private UserService userService;
    @GetMapping("/infoByUsername/{username}")
    public R<UserPO> infoByUsername(@PathVariable @Valid @ValidString(StringEnum.USERNAME_STRING) String username) {
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
