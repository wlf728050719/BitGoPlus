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
    Long CODE_LOCK_EXPIRE_SECONDS = 10L; //验证码重复发送间隔
    Long REGISTER_CODE_EXPIRE_SECONDS = 300L; //注册验证码超时时间
    Long CHANGE_PASSWORD_CODE_EXPIRE_SECONDS = 300L; //修改密码验证码超时时间
    Long PASSWORD_ERROR_EXPIRE_SECONDS = 300L; //密码多次错误缓冲时间
    Long LOGIN_CODE_EXPIRE_SECONDS = 300L; //登录验证码超时时间
}
