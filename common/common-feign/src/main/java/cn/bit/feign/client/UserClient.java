package cn.bit.feign.client;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.core.pojo.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * <p>用户服务Feign接口</p>
 * Date:2025/05/07 18:54:12
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(value = "user-service")
public interface UserClient {

    /**
     * 通过用户名获取对应用户基本信息列表(未脱敏+已删除)
     * @param username 用户名
     * @return R(用户基本信息列表/null)
     */
    @GetMapping("/api/user/userBaseInfosByUsername/{username}")
    R<List<UserBaseInfo>> getUserBaseInfosByUsername(@PathVariable("username") String username);

    /**
     * 通过用户ID获取对应用户基本信息(未脱敏+已删除)
     * @param userId 用户ID
     * @return R(用户基本信息/null)
     */
    @GetMapping("/api/user/userBaseInfoByUserId/{userId}")
    R<UserBaseInfo> getUserBaseInfoByUserId(@PathVariable("userId") Long userId);

    /**
     * 通过用户ID获取对应有效的用户权限集合
     * @param userId 用户ID
     * @return R(权限集合/null)
     */
    @GetMapping("/api/user/bitGoAuthorizationsByUserId/{userId}")
    R<Set<BitGoAuthorization>> getBitGoAuthorizationsByUserId(@PathVariable("userId") Long userId);

    /**
     * 添加用户ID对应可用用户添加权限
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @param roleCode 角色码
     * @return R(true)
     */
    @PostMapping("/api/user/addPermission")
    R<Boolean> addPermission(@RequestParam("userId") Long userId, @RequestParam("tenantId") Long tenantId,
        @RequestParam("roleCode") String roleCode);

    /**
     * 根据邮箱获取对应已验证邮箱用户基本信息(未脱敏+未删除)
     * @param email 邮箱
     * @return R(用户基本信息)
     */
    @GetMapping("/api/user/userBaseInfoByVerifiedEmail/{email}")
    R<UserBaseInfo> getUserBaseInfoByVerifiedEmail(@PathVariable("email") String email);
}
