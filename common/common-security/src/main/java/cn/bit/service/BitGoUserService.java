package cn.bit.service;

import cn.bit.pojo.dto.BitGoUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BitGoUserService extends UserDetailsService {
    BitGoUser getBitGoUserFromRPC(String username);
}
