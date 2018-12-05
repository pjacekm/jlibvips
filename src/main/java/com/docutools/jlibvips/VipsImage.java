package com.docutools.jlibvips;

import com.sun.jna.Pointer;

import java.nio.file.Path;

public class VipsImage {

    // TODO add documentation link
    public static final int POPPLER_LIMIT = 32767;

    public static VipsImage fromPdf(Path p) {
        var scale = 6.0f;
        VipsImage image;
        do {
            image = new VipsImage(Vips.pdfload(p.toString(), "scale", scale, null));
            scale -= 0.1f;
        } while(image.getWidth() > POPPLER_LIMIT || image.getHeight() > POPPLER_LIMIT);
        return image;
    }

    private final Pointer ptr;

    private VipsImage(final Pointer ptr) {
        this.ptr = ptr;
    }

    public int getWidth() {
        return VipsBindings.INSTANCE.vips_image_get_width(ptr);
    }

    public int getHeight() {
        return VipsBindings.INSTANCE.vips_image_get_height(ptr);
    }
}
