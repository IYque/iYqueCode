package cn.iyque.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeUtils {
    /**
     * 派号器workid：0~31
     * 机房datacenterid：0~31
     */
    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static Long nextId() {
        return snowflake.nextId();
    }
}
