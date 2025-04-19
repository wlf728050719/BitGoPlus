package cn.bit.userservice.service;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;

import java.util.List;
import java.util.Set;

public interface UserService {
    R<Boolean> registerByEmail(String code, String roleCode, UserBaseInfo user);

    R<Boolean> changePasswordByMail(String code, String email, String username, String password);

    R<List<UserBaseInfo>> getUserBaseInfosByEmail(String code, String email);

    R<Boolean> sendRegisterCodeByEmail(String email);

    R<Boolean> sendChangePasswordCodeByMail(String email);

    R<Boolean> sendGetUserBaseInfoCodeByEmail(String email);

    R<UserBaseInfo> getInfoByUsername(String username);

    R<UserBaseInfo> getInfoByUserId(Long userId);

    R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(Long userId);

    R<Boolean> setUserTenantIdByUserIdAndRoleCode(Long userId, Long tenantId, String roleCode);

}
