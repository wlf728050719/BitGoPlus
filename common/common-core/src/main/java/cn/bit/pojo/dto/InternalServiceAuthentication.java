package cn.bit.pojo.dto;

import cn.bit.constant.SecurityConstant;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

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
