package sk.leziemevpezinku.spring.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String formatTwoDecimal(BigDecimal value) {
        if (value == null) {
            return null;
        }
        NumberFormat formatter = getDecimalFormatter(2);
        return formatter.format(value.setScale(2, RoundingMode.HALF_DOWN));
    }

    private static NumberFormat getDecimalFormatter(int decimalPlaces) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance(Locale.getDefault());
        numberInstance.setGroupingUsed(true);
        numberInstance.setMinimumFractionDigits(decimalPlaces);
        numberInstance.setMaximumFractionDigits(decimalPlaces);
        return numberInstance;
    }
}
