package com.cblib.util;

import android.content.res.Resources;

public class CBUtil {

    /**
     * Do not initialze instance for static class only
     */
    private CBUtil() {
    }

    /**
     * Scale size
     *
     * @param _resources
     * @param size
     * @return
     */
    public static float scaleSize(Resources _resources, int size) {
        float scale = _resources.getDisplayMetrics().scaledDensity;
        int textSizeP = (int) (size / scale);
        return (float) textSizeP;
    }
}
