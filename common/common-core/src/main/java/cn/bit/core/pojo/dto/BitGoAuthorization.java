package cn.bit.core.pojo.dto;

import cn.bit.core.constant.SecurityConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class BitGoAuthorization implements GrantedAuthority {
    private String roleCode;
    private Long tenantId;

    @Override
    public String getAuthority() {
        return SecurityConstant.ROLE_PREFIX + roleCode;
    }
}
