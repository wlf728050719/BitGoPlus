package cn.bit.constant;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface MessageConstant {
    String DUPLICATE_USERNAME = "存在同名用户名";
    String DUPLICATE_EMAIL = "邮箱已被验证，请使用其他邮箱";
    String DUPLICATE_PERMISSION = "已有对应租户角色权限";
    String USERNAME_NOT_EXIST = "不存在对应用户名";
    String ACCOUNT_LOCKED = "账号被冻结,请联系管理员解封";
    String ACCOUNT_DELETED = "账号被注销";
}
