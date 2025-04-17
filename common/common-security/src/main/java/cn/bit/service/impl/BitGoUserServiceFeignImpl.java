package cn.bit.service.impl;

import cn.bit.constant.SecurityConstant;
import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.service.BitGoUserService;
import cn.bit.exception.BizException;
import cn.bit.exception.SysException;
import cn.bit.client.UserClient;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.vo.R;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;


@Service("BitGoUserService")
@AllArgsConstructor
public class BitGoUserServiceFeignImpl implements BitGoUserService {
    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getBitGoUserFromRPC(username);
    }

    public BitGoUser getBitGoUserFromRPC(String username) {
        // 获取用户基本信息
        R<UserBaseInfo> userResponse = userClient.getInfoByUsername(username);
        if (userResponse == null) {
            throw new SysException("get response from user-service failed");
        }
        if (userResponse.getData() == null) {
            throw new BizException("用户名不存在");
        }
        UserBaseInfo user = userResponse.getData();

        // 获取用户角色信息
        R<Set<BitGoAuthorization>> roleResponse = userClient.getBitGoAuthorizationByUserId(user.getUserId());
        if (roleResponse == null) {
            throw new SysException("get response from user-service failed");
        }
        // 构建BitGoUser对象
        return new BitGoUser(user, roleResponse.getData());
    }

    @Override
    public boolean checkUser(BitGoUser user, Long userId) {
        if (user == null) {
            return false;
        }
        return user.getUserId().equals(userId);
    }

    @Override
    public boolean checkAdmin(BitGoUser user) {
        return checkRoleAndTenantId(user, null, SecurityConstant.ROLE_ADMIN);
    }

    @Override
    public boolean checkShopKeeper(BitGoUser user, Long tenantId) {
        return checkRoleAndTenantId(user, tenantId, SecurityConstant.ROLE_SHOPKEEPER);
    }

    @Override
    public boolean checkClerk(BitGoUser user, Long tenantId) {
        return checkRoleAndTenantId(user, tenantId, SecurityConstant.ROLE_CLERK);
    }

    private boolean checkRoleAndTenantId(BitGoUser user, Long tenantId, String roleCode) {
        if (user == null) {
            return false;
        }

        // 获取用户的所有授权信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // 检查是否有匹配的角色
        return authorities.stream()
                .filter(auth -> auth instanceof BitGoAuthorization)
                .map(auth -> (BitGoAuthorization) auth)
                .anyMatch(auth -> {
                    // 1. 先检查角色是否匹配
                    boolean roleMatches = auth.getRoleCode().equals(roleCode);
                    // 2. 如果角色不匹配，直接返回 false
                    if (!roleMatches) {
                        return false;
                    }
                    // 3. 如果角色匹配，且不需要检查租户（tenantId == null），则直接返回 true
                    if (tenantId == null) {
                        return true;
                    }
                    // 4. 如果需要检查租户，则检查该角色的租户是否匹配
                    return tenantId.equals(auth.getTenantId());
                });
    }

}
