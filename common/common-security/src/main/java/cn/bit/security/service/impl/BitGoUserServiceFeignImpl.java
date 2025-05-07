package cn.bit.security.service.impl;

import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;
import cn.bit.security.service.BitGoUserService;
import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.feign.client.UserClient;
import cn.bit.core.pojo.dto.security.BitGoUser;
import cn.bit.core.pojo.vo.R;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("BitGoUserService")
@AllArgsConstructor
public class BitGoUserServiceFeignImpl implements BitGoUserService {
    private final UserClient userClient;

    /**
     * 根据用户名加载可用用户鉴权实体类
     * @param username 用户名
     * @return 可用用户鉴权实体类（BitGoUser）
     * @throws BizException 用户状态相关业务异常
     * @throws SysException feign调用系统异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws BizException, SysException {
        // 获取对应用户名的全体用户
        R<List<UserBaseInfo>> userResponse = userClient.getUserBaseInfosByUsername(username);
        if (userResponse == null) {
            throw new SysException("get response from user-service failed");
        }
        List<UserBaseInfo> userBaseInfos = userResponse.getData();
        if (userBaseInfos == null || userBaseInfos.isEmpty()) {
            throw new BizException("用户名不存在");
        }
        // 获取其中唯一的未被删除的用户
        UserBaseInfo user =
            userBaseInfos.stream().filter(userBaseInfo -> userBaseInfo.getDelFlag() == 0).findFirst().orElse(null);
        if (user == null) {
            throw new BizException("用户已被注销");
        }
        if (user.getLockFlag() == 1) {
            throw new BizException("用户已被冻结，请联系管理员解封");
        }
        // 获取用户角色信息
        R<Set<BitGoAuthorization>> roleResponse = userClient.getBitGoAuthorizationsByUserId(user.getUserId());
        if (roleResponse == null) {
            throw new SysException("get response from user-service failed");
        }
        // 构建BitGoUser对象
        return new BitGoUser(user, roleResponse.getData());
    }

    @Override
    public boolean checkAdmin(BitGoUser user) {
        return checkRole(user, SecurityConstant.ROLE_ADMIN);
    }

    @Override
    public boolean checkShopKeeper(BitGoUser user) {
        return checkRole(user, SecurityConstant.ROLE_SHOPKEEPER);
    }

    @Override
    public boolean checkClerk(BitGoUser user) {
        return checkRole(user, SecurityConstant.ROLE_CLERK);
    }

    private boolean checkRole(BitGoUser user, String roleCode) {
        if (user == null) {
            return false;
        }
        // 获取用户的所有授权信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // 检查是否有匹配的角色
        return authorities.stream().filter(auth -> auth instanceof BitGoAuthorization)
            .map(auth -> (BitGoAuthorization) auth).anyMatch(auth -> auth.getRoleCode().equals(roleCode));
    }
}
