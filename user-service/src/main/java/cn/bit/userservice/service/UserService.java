package cn.bit.userservice.service;

import cn.bit.core.pojo.dto.security.BitGoAuthorization;
import cn.bit.core.pojo.dto.user.UserBaseInfo;

import java.util.List;
import java.util.Set;

public interface UserService {
    Boolean addPermission(String roleCode, Long tenantId, Long userId);

    Boolean registerByEmail(String code, UserBaseInfo user);

    Boolean changePasswordByMail(String code, String email, String password);

    Boolean sendRegisterCodeByEmail(String email);

    Boolean sendChangePasswordCodeByMail(String email);

    List<UserBaseInfo> getUserBaseInfosByUsername(String username);

    UserBaseInfo getUserBaseInfoByUserId(Long userId);

    Set<BitGoAuthorization> getAvailableBitGoAuthorizationsByUserId(Long userId);
}

