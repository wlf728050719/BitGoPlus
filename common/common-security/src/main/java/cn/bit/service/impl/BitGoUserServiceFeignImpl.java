package cn.bit.service.impl;

import cn.bit.service.BitGoUserService;
import cn.bit.constant.SecurityConstant;
import cn.bit.exception.BizException;
import cn.bit.exception.SysException;
import cn.bit.feign.client.UserClient;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.po.RoleDictItem;
import cn.bit.pojo.po.UserPO;
import cn.bit.pojo.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BitGoUserServiceFeignImpl implements BitGoUserService {
    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getBitGoUserFromRPC(username);
    }

    public BitGoUser getBitGoUserFromRPC(String username) {
        // 获取用户基本信息
        R<UserPO> userResponse = userClient.infoByUsername(username);
        if (userResponse == null) {
            throw new SysException("用户服务调用失败");
        }
        if (userResponse.getData() == null) {
            throw new BizException("用户名不存在");
        }
        UserPO user = userResponse.getData();

        // 获取用户角色信息
        R<Set<RoleDictItem>> roleResponse = userClient.rolesByUserId(user.getUserId());
        if (roleResponse == null) {
            throw new SysException("用户服务调用失败");
        }
        Set<RoleDictItem> roles = roleResponse.getData();

        // 将角色转换为权限
        Collection<? extends GrantedAuthority> authorities =
            roles.stream().map(role -> new SimpleGrantedAuthority(SecurityConstant.ROLE + role.getRoleCode()))
                .collect(Collectors.toSet());

        // 构建BitGoUser对象
        return new BitGoUser(user, roles, authorities);
    }
}
