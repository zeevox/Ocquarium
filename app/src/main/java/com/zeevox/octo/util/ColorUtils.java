/*
 * Copyright (C) 2015 Aidan "afollestad" Follestad
 * Copyright (C) 2018 Timothy "ZeevoX" Langer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
