package org.jlibvips.util;

import com.sun.jna.Pointer;
import org.jlibvips.jna.VipsBindingsSingleton;

public class VipsUtils {

    public static Integer booleanToInteger(Boolean b) {
        return b == null? null : (b? 1 : 0);
    }

    public static Integer toOrdinal(Enum v) {
        return v != null? v.ordinal() : null;
    }

    public static Pointer toPointer(double[] array) {
        return VipsBindingsSingleton.instance().vips_array_double_new(array, array.length);
    }

    public static double[] toRGA(long hex) {
        double[] rgba = new double[4];
        rgba[3] = (hex & 0xFF000000) >> 24;
        rgba[0] = (hex & 0xFF0000) >> 16;
        rgba[1] = (hex & 0xFF00) >> 8;
        rgba[2] = (hex & 0xFF);
        return rgba;
    }
}
