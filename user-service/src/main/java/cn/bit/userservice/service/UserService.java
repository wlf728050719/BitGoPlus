package cn.bit.userservice.service;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;

import java.util.Set;

public interface UserService {
    Boolean addPermission(String roleCode, Long tenantId, Long userId);

    Boolean registerByEmail(String code, UserBaseInfo user);

    Boolean changePasswordByMail(String code, String email, String password);

    Boolean sendRegisterCodeByEmail(String email);

    Boolean sendChangePasswordCodeByMail(String email);

    UserBaseInfo getUndeletedUserBaseInfoByUsername(String username);

    UserBaseInfo getInfoByUserId(Long userId);

    Set<BitGoAuthorization> getAvailableBitGoAuthorizationByUserId(Long userId);
}
