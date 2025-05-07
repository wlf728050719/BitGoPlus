package cn.bit.core.pojo.dto.security;

import cn.bit.core.constant.SecurityConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>权限类</p>
 * Date:2025/05/07 20:26:42
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BitGoAuthorization implements GrantedAuthority {
    private String roleCode;
    private Long tenantId;

    @Override
    public String getAuthority() {
        return SecurityConstant.ROLE_PREFIX + roleCode + tenantId;
    }
}
