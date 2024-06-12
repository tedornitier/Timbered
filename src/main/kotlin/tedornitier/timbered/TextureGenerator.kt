package tedornitier.timbered

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val woodDir = File("texture_generator/wood")
    val maskDir = File("texture_generator/masks")
    val background = ImageIO.read(File("texture_generator/background.png"))

    val woodFiles = woodDir.listFiles { _, name -> name.endsWith(".png") } ?: emptyArray()
    val maskFiles = maskDir.listFiles { _, name -> name.endsWith(".png") } ?: emptyArray()
    
    val woodenImages = woodFiles.map { ImageIO.read(it) }
    val masks = maskFiles.map { ImageIO.read(it) }

    val names = mutableListOf<String>()
    val outputImages = woodenImages.flatMapIndexed { woodIndex, woodImage ->
        masks.mapIndexed { maskIndex, mask ->
            val woodName = woodFiles[woodIndex].nameWithoutExtension
            val maskName = maskFiles[maskIndex].nameWithoutExtension
            val resultName = "${maskName}_${woodName}"
            names.add(resultName)
            applyMaskAndBackground(woodImage, mask, background)
        }
    }

    outputImages.forEachIndexed { index, image ->
        ImageIO.write(image, "png", File("texture_generator/output/${names[index]}.png"))
    }
}

fun applyMaskAndBackground(woodImage: BufferedImage, mask: BufferedImage, background: BufferedImage): BufferedImage {
    val width = woodImage.width
    val height = woodImage.height
    val result = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    
    for (x in 0 until width) {
        for (y in 0 until height) {
            val maskPixel = Color(mask.getRGB(x, y), true)
            val woodPixel = Color(woodImage.getRGB(x, y), true)
            val backgroundPixel = Color(background.getRGB(x, y), true)
            
            result.setRGB(x, y, if (maskPixel.red == 0 && maskPixel.green == 0 && maskPixel.blue == 0) {
                woodPixel.rgb
            } else {
                backgroundPixel.rgb
            })
        }
    }
    return result
}
