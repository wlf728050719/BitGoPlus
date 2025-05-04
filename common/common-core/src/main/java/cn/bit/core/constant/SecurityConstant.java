package cn.bit.core.constant;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface SecurityConstant {
    String ROLE_PREFIX = "ROLE_";
    String ACCESS_TOKEN = "access_token";
    String REFRESH_TOKEN = "refresh_token";
    /** header */
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_SOURCE = "Source";
    /** tag */
    String TAG_INTERNAL = "Internal ";
    String TAG_BEARER = "Bearer ";
    String TAG_SERVICE = "Service ";
    /** role */
    String ROLE_INTERNAL_SERVICE = "internal_service";
    String ROLE_ADMIN = "admin";
    String ROLE_CUSTOMER = "customer";
    String ROLE_SHOPKEEPER = "shopkeeper";
    String ROLE_CLERK = "clerk";
}
