package dev.dannytaylor.perspective.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberHelper {
    public static String floatToString(float amount) {
        return floatToString("#.##", amount);
    }

    public static String floatToString(String pattern, float amount) {
        return new DecimalFormat(pattern).format(amount);
    }

    public static float formatFloat(float amount) {
        return formatFloat(2, amount);
    }

    public static float formatFloat(int scale, float amount) {
        return BigDecimal.valueOf(amount).setScale(scale, RoundingMode.HALF_UP).floatValue();
    }
}
