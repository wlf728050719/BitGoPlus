package cn.bit.core.pojo.dto.security;


import cn.bit.core.pojo.dto.user.UserBaseInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * <p>鉴权实体类</p>
 * Date:2025/05/07 20:27:42
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class BitGoUser extends User {
    private final UserBaseInfo userBaseInfo;

    public BitGoUser(UserBaseInfo userBaseInfo, Collection<? extends GrantedAuthority> authorities) {
        super(userBaseInfo.getUsername(), userBaseInfo.getPassword(), authorities);
        this.userBaseInfo = userBaseInfo;
    }
}
