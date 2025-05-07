package cn.bit.core.constant;

/**
 * <p>安全常量</p>
 * Date:2025/05/07 20:17:47
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
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
    //密码错误最大次数
    int MAX_PASSWORD_ERROR_COUNT = 5;
}
