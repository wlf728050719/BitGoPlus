package cn.bit.userservice.service;

import cn.bit.pojo.dto.BitGoAuthorization;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;

import java.util.Set;

public interface UserService {
    R<Boolean> registerByEmail(String code, String roleCode, UserBaseInfo user);

    R<Boolean> changePasswordByMail(String username, String code, String email, String password);

    R<Boolean> sendRegisterCodeByEmail(String email);

    R<Boolean> sendChangePasswordCodeByMail(String email);

    R<UserBaseInfo> getInfoByUsername(String username);

    R<UserBaseInfo> getInfoByUserId(Long userId);

    R<Set<BitGoAuthorization>> getBitGoAuthorizationByUserId(Long userId);

}
