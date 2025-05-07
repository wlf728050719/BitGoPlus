package cn.bit.userservice.controller;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.core.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class APIController {
    private UserService userService;

    @GetMapping("/userBaseInfosByUsername/{username}")
    public R<List<UserBaseInfo>> getUserBaseInfosByUsername(@PathVariable("username") String username) {
        return R.ok(userService.getUserBaseInfosByUsername(username));
    }

    @GetMapping("/userBaseInfoByUserId/{userId}")
    public R<UserBaseInfo> getUserBaseInfoByUserId(@PathVariable("userId") Long userId) {
        return R.ok(userService.getUserBaseInfoByUserId(userId));
    }

    @GetMapping("/bitGoAuthorizationsByUserId/{userId}")
    public R<Set<BitGoAuthorization>> getBitGoAuthorizationsByUserId(@PathVariable("userId") Long userId) {
        return R.ok(userService.getAvailableBitGoAuthorizationsByUserId(userId));
    }

    @PostMapping("/addPermission")
    public R<Boolean> addPermission(@RequestParam("userId") Long userId, @RequestParam("tenantId") Long tenantId,
        @RequestParam("roleCode") String roleCode) {
        return R.ok(userService.addPermission(roleCode, tenantId, userId));
    }

}
