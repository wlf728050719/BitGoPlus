package cn.bit.constant;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface RedisExpire {
    Long CODE_LOCK_EXPIRE_SECONDS = 10L;
    Long REGISTER_CODE_EXPIRE_SECONDS = 300L;
    Long CHANGE_PASSWORD_CODE_EXPIRE_SECONDS = 300L;
}
