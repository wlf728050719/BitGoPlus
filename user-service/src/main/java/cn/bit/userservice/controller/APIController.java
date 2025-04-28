package cn.bit.userservice.controller;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class APIController {
    private UserService userService;

    @GetMapping("/infoByUsername/{username}")
    public R<UserBaseInfo> getInfoByUsername(@PathVariable("username") String username) {
        return userService.getInfoByUsername(username);
    }

    @GetMapping("/infoByUserId/{userId}")
    public R<UserBaseInfo> getInfoByUserId(@PathVariable("userId") Long userId) {
        return userService.getInfoByUserId(userId);
    }

    @GetMapping("/bitGoAuthorizationByUserId/{userId}")
    public R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(@PathVariable("userId") Long userId) {
        return userService.getBitGoAuthorizationByUserId(userId);
    }

    @GetMapping("/setUserTenantIdByUserIdAndRoleCode")
    public R<Boolean> setUserTenantIdByUserIdAndRoleCode(@RequestParam("userId") Long userId,
        @RequestParam("tenantId") Long tenantId, @RequestParam("roleCode") String roleCode) {
        return userService.setUserTenantIdByUserIdAndRoleCode(userId, tenantId, roleCode);
    }

}
