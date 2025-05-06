package cn.bit.feign.client;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.core.pojo.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "user-service")
public interface UserClient {

    @GetMapping("/api/user/undeletedUserBaseInfoByUsername/{username}")
    R<UserBaseInfo> getUndeletedUserBaseInfoByUsername(@PathVariable("username") String username);

    @GetMapping("/api/user/userBaseInfoByUserId/{userId}")
    R<UserBaseInfo> getUserBaseInfoByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/user/bitGoAuthorizationByUserId/{userId}")
    R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/api/user/addPermission")
    R<Boolean> addPermission(@RequestParam("userId") Long userId, @RequestParam("tenantId") Long tenantId,
        @RequestParam("roleCode") String roleCode);
}
