package org.jlibvips

import org.jlibvips.jna.VipsBindingsSingleton
import org.jlibvips.util.VipsUtils
import spock.lang.Specification

import java.nio.file.Files

import static org.jlibvips.TestUtils.copyResourceToFS

class Drawing extends Specification {

    def setupSpec() {
        VipsBindingsSingleton.configure("libvips.42.dylib")
    }

    def "Draw a colored rect with SVG."() {
        given:
        def svg = """<svg height="100" width="100">
    <rect width="300" height="100" style="fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)" />
  </svg>"""
        def imagePath = copyResourceToFS "500x500.jpg"
        def image = VipsImage.fromFile imagePath
        when:
        def svgImage = VipsImage.fromString svg
        def newImage = image.insert(svgImage)
            .x(10)
            .y(10)
            .create()
        then:
        newImage != null
        cleanup:
        Files.deleteIfExists imagePath
    }

    def "Convert colored image to b-w."() {
        given:
        def imagePath = copyResourceToFS "900x700.png"
        def image = VipsImage.fromFile imagePath
        when:
        def bwImage = image.toColorspace(VipsInterpretation.B_W)
        then:
        bwImage != null
        cleanup:
        Files.deleteIfExists imagePath
    }

    def "Convert white to transparent."() {
        given:
        def imagePath = copyResourceToFS "900x700.png"
        def image = VipsImage.fromFile imagePath
        when:
        def bwImage = image.removeColor(0xFFAAAAAA)
        then:
        bwImage != null
        cleanup:
        Files.deleteIfExists imagePath
    }

    def "Should tint."() {
        given:
        def imagePath = copyResourceToFS "fuel.png"
        def image = VipsImage.fromFile imagePath
        when:
        def tinted = image
            .toColorspace(VipsInterpretation.B_W)
            .removeColor(0xFF999999)
            .tint(0xFF00FF00)
        then:
        tinted != null
        cleanup:
        Files.deleteIfExists imagePath
    }

    def "Convert hexa to rgba."() {
        given:
        long hex = 0xFFABF790
        when:
        double[] rgba = VipsUtils.toRGA(hex)
        then:
        rgba[0] == 171
        rgba[1] == 247
        rgba[2] == 144
        rgba[3] == 255
    }
}
