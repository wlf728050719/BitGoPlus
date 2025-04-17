package cn.bit.client;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(value = "user-service")
public interface UserClient {

    @GetMapping("/api/user/infoByUsername/{username}")
    R<UserBaseInfo> getInfoByUsername(@PathVariable("username") String username);

    @GetMapping("/api/user/infoByUserId/{userId}")
    R<UserBaseInfo> getInfoByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/user/bitGoAuthorizationByUserId/{userId}")
    R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(@PathVariable("userId") Long userId);
}

