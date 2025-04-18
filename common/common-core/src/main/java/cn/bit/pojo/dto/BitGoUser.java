package cn.bit.pojo.dto;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;



@Getter
public class BitGoUser extends User {
    private final UserBaseInfo userBaseInfo;

    public BitGoUser(UserBaseInfo userBaseInfo, Collection<? extends GrantedAuthority> authorities) {
        super(userBaseInfo.getUsername(), userBaseInfo.getPassword(), authorities);
        this.userBaseInfo = userBaseInfo;
    }
}
