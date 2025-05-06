package cn.bit.security.util;

import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.security.BitGoUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@UtilityClass
public class SecurityUtils {
    /**
     * 获取用户
     */
    public BitGoUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BizException("用户未登录,请登录后访问");
        }
        if (!(authentication.getPrincipal() instanceof BitGoUser)) {
            throw new SysException("user permission parsing error");
        }
        return (BitGoUser) authentication.getPrincipal();
    }

    /**
     * 判断用户租户ID以及角色是否合法
     * @param tenantId 租户ID
     * @param roleCode 角色
     */
    public void checkTenantId(Long tenantId, String roleCode) {
        BitGoUser user = getUser();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 遍历权限，查找匹配的角色和租户ID
        boolean isMatch = authorities.stream().filter(auth -> auth instanceof BitGoAuthorization)
            .map(auth -> (BitGoAuthorization) auth).anyMatch(auth -> auth.getRoleCode().equals(roleCode)
                && (auth.getTenantId() != null && auth.getTenantId().equals(tenantId)));
        if (!isMatch) {
            throw new BizException("未授权的访问");
        }
    }
}
