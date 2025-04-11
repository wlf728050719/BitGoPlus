package cn.bit.feign.client;

import cn.bit.pojo.po.RoleDictItem;
import cn.bit.pojo.po.UserPO;
import cn.bit.pojo.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(value = "user-service")
public interface UserClient {

    @GetMapping("/infoByUsername/{username}")
    R<UserPO> infoByUsername(@PathVariable("username") String username);

    @GetMapping("/infoByUserId/{userId}")
    R<UserPO> infoByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/rolesByUserId/{userId}")
    R<Set<RoleDictItem>> rolesByUserId(@PathVariable("userId") Long userId);
}

