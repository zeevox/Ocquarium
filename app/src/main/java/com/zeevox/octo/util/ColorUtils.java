package com.zeevox.octo.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class ColorUtils {
    /* From https://github.com/kabouzeid/app-theme-helper/blob/master/library/src/main/java/com/kabouzeid/appthemehelper/util/ColorUtil.java#L36 */
    public static boolean isColorLight(@ColorInt int color) {
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }
}
