package cn.bit.constant;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface RedisKey {
    // 键分隔符
    String SEPARATOR = ":";
    // 命名空间
    String NAMESPACE = "BitGO";
    // 一级前缀
    String DICT_PREFIX = "Dict";
    String SNOWFLAKE_PREFIX = "Snowflake";
    String CODE_PREFIX = "Code";
    String TOKEN_PREFIX = "Token";
    // 二级前缀
    String REGISTER_PREFIX = "Register";
    String CHANGE_PASSWORD_PREFIX = "ChangePassword";
    String GET_USER_BASE_INFO_PREFIX = "GetUserBaseInfo";
    String LOCK_PREFIX = "Lock";
    // 键格式
    // token
    String TOKEN_KEY_FORMAT = NAMESPACE + SEPARATOR + TOKEN_PREFIX + SEPARATOR + "Username-%s";
    // codeLock
    String CODE_MAIL_LOCK = NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + LOCK_PREFIX + SEPARATOR + "Mail-%s";
    // snowflake
    String SNOWFLAKE_KEY_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Worker-%d";
    String SNOWFLAKE_LOCK_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Lock";
    // register
    String CODE_REGISTER_MAIL_KEY_FORMAT =
        NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + REGISTER_PREFIX + SEPARATOR + "Mail-%s";
    // changePwd
    String CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT =
        NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + CHANGE_PASSWORD_PREFIX + SEPARATOR + "Mail-%s";
    // getUsername
    String CODE_GET_USER_BASE_INFO_MAIL_KEY_FORMAT =
        NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + GET_USER_BASE_INFO_PREFIX + SEPARATOR + "Mail-%s";
}
