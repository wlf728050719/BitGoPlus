package cn.bit.service;

import cn.bit.pojo.dto.BitGoUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BitGoUserService extends UserDetailsService {
    boolean checkUser(BitGoUser user, Long userId);

    boolean checkAdmin(BitGoUser user);

    boolean checkShopKeeper(BitGoUser user, Long tenantId);

    boolean checkClerk(BitGoUser user, Long tenantId);
}
