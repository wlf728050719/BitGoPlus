package cn.bit.constant;

public class RedisKey {
    // 键分隔符
    public static final String SEPARATOR = ":";
    // 命名空间
    public static final String NAMESPACE = "BitGO";
    // 一级前缀
    public static final String DICT_PREFIX = "Dict";
    public static final String SNOWFLAKE_PREFIX = "Snowflake";
    // 键格式
    public static final String SNOWFLAKE_KEY_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Worker-%d";
    public static final String SNOWFLAKE_LOCK_FORMAT =
        NAMESPACE + SEPARATOR + SNOWFLAKE_PREFIX + SEPARATOR + "S-%s" + SEPARATOR + "DC-%d" + SEPARATOR + "Lock";
}
