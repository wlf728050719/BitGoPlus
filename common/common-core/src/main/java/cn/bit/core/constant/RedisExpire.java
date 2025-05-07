package cn.bit.core.constant;

/**
 * <p>Redis键过期时间</p>
 * Date:2025/05/07 20:16:47
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:InterfaceIsType")
public interface RedisExpire {
    Long CODE_LOCK_EXPIRE_SECONDS = 10L;
    Long REGISTER_CODE_EXPIRE_SECONDS = 300L;
    Long CHANGE_PASSWORD_CODE_EXPIRE_SECONDS = 300L;
    Long PASSWORD_ERROR_EXPIRE_SECONDS = 300L;
}
