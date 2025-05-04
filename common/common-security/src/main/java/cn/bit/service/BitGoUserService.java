package cn.bit.service;

import cn.bit.pojo.dto.BitGoUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BitGoUserService extends UserDetailsService {

    boolean checkAdmin(BitGoUser user);

    boolean checkShopKeeper(BitGoUser user);

    boolean checkClerk(BitGoUser user);
}
