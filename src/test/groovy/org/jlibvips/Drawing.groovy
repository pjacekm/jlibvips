package org.jlibvips

import org.jlibvips.jna.VipsBindingsSingleton
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
        image.unref()
        svgImage.unref()
        newImage.unref()
        Files.deleteIfExists imagePath
    }

}
