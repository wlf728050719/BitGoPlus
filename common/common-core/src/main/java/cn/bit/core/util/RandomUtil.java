package cn.bit.core.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

/**
 * <p>随机数生成工具</p>
 * Date:2025/05/07 20:30:47
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@UtilityClass
public class RandomUtil {
    public String getRandomNumberString(int length) {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // 生成0到9之间的随机数字
            int digit = random.nextInt(10);
            randomNumber.append(digit);
        }
        return randomNumber.toString();
    }
}
