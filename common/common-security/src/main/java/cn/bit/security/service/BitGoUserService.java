package cn.bit.security.service;

import cn.bit.core.pojo.dto.BitGoUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BitGoUserService extends UserDetailsService {

    boolean checkAdmin(BitGoUser user);

    boolean checkShopKeeper(BitGoUser user);

    boolean checkClerk(BitGoUser user);
}
