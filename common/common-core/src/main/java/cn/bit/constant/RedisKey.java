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
    // 二级前缀
    String REGISTER_PREFIX = "Register";
    String CHANGE_PASSWORD_PREFIX = "ChangePassword";
    String LOCK_PREFIX = "Lock";
    // 键格式
    String SNOWFLAKE_KEY_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Worker-%d";
    String SNOWFLAKE_LOCK_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Lock";
    String CODE_REGISTER_MAIL_KEY_FORMAT =
        NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + REGISTER_PREFIX + SEPARATOR + "Mail-%s";
    String CODE_REGISTER_MAIL_LOCK_FORMAT = NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + REGISTER_PREFIX
        + SEPARATOR + LOCK_PREFIX + SEPARATOR + "Mail-%s";
    String CODE_CHANGE_PASSWORD_MAIL_KEY_FORMAT =
        NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR + CHANGE_PASSWORD_PREFIX + SEPARATOR + "Mail-%s";
    String CODE_CHANGE_PASSWORD_MAIL_LOCK_FORMAT = NAMESPACE + SEPARATOR + CODE_PREFIX + SEPARATOR
        + CHANGE_PASSWORD_PREFIX + SEPARATOR + LOCK_PREFIX + SEPARATOR + "Mail-%s";
}
