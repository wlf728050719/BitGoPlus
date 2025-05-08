package cn.bit.authservice.service;

import java.util.Map;

public interface AuthenticationService {
    Map<String, String> loginByUsernameAndPassword(String username, String password);

    Boolean sendLoginCodeByEmail(String email);

    Map<String, String> loginByEmailAndCode(String email, String code);

    String refreshToken(String refreshToken);
}
