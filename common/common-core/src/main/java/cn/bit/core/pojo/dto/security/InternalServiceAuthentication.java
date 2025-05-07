package cn.bit.core.pojo.dto.security;

import cn.bit.core.constant.SecurityConstant;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * <p>内部权限类</p>
 * Date:2025/05/07 20:28:00
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class InternalServiceAuthentication extends AbstractAuthenticationToken {

    private final String serviceName;

    public InternalServiceAuthentication(String serviceName, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.serviceName = serviceName;
        super.setAuthenticated(true);
    }

    public InternalServiceAuthentication(String serviceName) {
        this(serviceName,
            AuthorityUtils.createAuthorityList(SecurityConstant.ROLE_PREFIX + SecurityConstant.ROLE_INTERNAL_SERVICE));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return serviceName;
    }
}
