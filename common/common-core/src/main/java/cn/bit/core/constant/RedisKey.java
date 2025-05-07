package cn.bit.core.constant;

/**
 * <p>Redis键</p>
 * Date:2025/05/07 20:17:05
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:InterfaceIsType")
public interface RedisKey {
    // 键分隔符
    String SEPARATOR = ":";
    // 命名空间
    String NAMESPACE = "BitGO"; // 命名空间
    // 一级前缀
    String DICT_PREFIX = "Dict"; // 字典项
    String SNOWFLAKE_PREFIX = "Snowflake"; // 雪花算法
    String CODE_PREFIX = "Code"; // 验证码
    String TOKEN_PREFIX = "Token"; // accessToken
    String COUNT_PREFIX = "Count"; // 计数
    // 二级前缀
    String REGISTER_PREFIX = "Register";
    String CHANGE_PASSWORD_PREFIX = "ChangePassword";
    String PASSWORD_ERROR_COUNT = "PasswordErrorCount";
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
    // passwordError
    String PASSWORD_ERROR_COUNT_KEY_FORMAT =
        NAMESPACE + SEPARATOR + COUNT_PREFIX + SEPARATOR + PASSWORD_ERROR_COUNT + SEPARATOR + "Username-%s";
}
