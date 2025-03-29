package cn.bit.feign.client;

import cn.bit.common.pojo.dto.UserBaseInfoDTO;
import cn.bit.common.pojo.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "user-service")
public interface UserClient {
    @GetMapping("/user/baseInfo/{id}")
    R<UserBaseInfoDTO> getUserBaseInfo(@PathVariable("id") Integer id, @RequestHeader("source") String source);
}
