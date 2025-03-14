package com.linebeck.basic.utilities;

public class NumericUtil {

    public static boolean isNumeric(String string) {
        if (string == null) return false;
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
