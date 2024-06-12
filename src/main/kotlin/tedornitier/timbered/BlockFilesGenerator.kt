package tedornitier.timbered

import tedornitier.timbered.block.TimberedBlocks.blocks
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.io.File

fun createModelBlock(
    prefix: String, blockName: String, woodType: String, sides: Pair<String, String>? = null
): String {
    val texture = "$prefix/${blockName}" + (sides?.let { "_${it.first}" } ?: "") + "_$woodType"
    val mirroredTexture = sides?.let { "$prefix/${blockName}_${sides.second}_$woodType" } ?: texture
    return """{
  "parent": "block/cube",
  "textures": {
    "particle": "$texture",
    "north": "$texture",
    "south": "$texture",
    "east": "$mirroredTexture",
    "west": "$mirroredTexture",
    "up": "$texture",
    "down": "$texture"
  }
}"""
}

fun createBlockState(blockPrefix: String, blockName: String, woodType: String): String {
    return """{
  "variants": {
    "": { "model": "$blockPrefix/${blockName}_$woodType" }
  }
}"""
}

fun main() {
    val blockPrefix = "timbered:block"
    val sameTextureBlockNames = listOf("square_block", "square_block_cross")
    val mirroredTextureBlockModelsNames = listOf("square_block_diagonal", "square_block_high_diagonal_bottom", "square_block_high_diagonal_top")
    woodTypes.forEach { woodType ->
        sameTextureBlockNames.forEach { blockName ->
            File("src/main/resources/assets/timbered/models/block/${blockName}_$woodType.json").let {
                it.writeText(createModelBlock(blockPrefix, blockName, woodType))
                println("Generated JSON for simple $blockName $woodType model block: ${it.absolutePath}")
            }
        }
        mirroredTextureBlockModelsNames.forEach { blockName ->
            File("src/main/resources/assets/timbered/models/block/${blockName}_$woodType.json").let {
                it.writeText(createModelBlock(blockPrefix, blockName, woodType, Pair("left", "right")))
                println("Generated JSON for mirrored $blockName $woodType model block: ${it.absolutePath}")
            }
        }
        sameTextureBlockNames.forEach { blockName ->
            File("src/main/resources/assets/timbered/blockstates/${blockName}_$woodType.json").let {
                it.writeText(createBlockState(blockPrefix, blockName, woodType))
                println("Generated JSON for blockstates $blockName $woodType: ${it.absolutePath}")
            }
        }
        File("src/main/resources/assets/timbered/blockstates/square_block_diagonal_$woodType.json").let {
            it.writeText(
                """
                {
                  "variants": {
                    "facing_y_axis=true": { "model": "timbered:block/square_block_diagonal_$woodType" },
                    "facing_y_axis=false": { "model": "timbered:block/square_block_diagonal_$woodType", "y": 90 }
                  }
                }
            """.trimIndent()
            )
            println("Generated JSON for blockstates square_block_diagonal $woodType: ${it.absolutePath}")
        }
        File("src/main/resources/assets/timbered/blockstates/square_block_diagonal_$woodType.json").let {
            it.writeText(
                """
                {
                  "variants": {
                    "facing_y_axis=true": { "model": "timbered:block/square_block_diagonal_$woodType" },
                    "facing_y_axis=false": { "model": "timbered:block/square_block_diagonal_$woodType", "y": 90 }
                  }
                }
            """.trimIndent()
            )
            println("Generated JSON for blockstates square_block_diagonal $woodType: ${it.absolutePath}")
        }
        File("src/main/resources/assets/timbered/blockstates/square_block_high_diagonal_$woodType.json").let {
            it.writeText(
                """
                {
                  "variants": {
                    "facing_y_axis=true,bottom=false": { "model": "timbered:block/square_block_high_diagonal_top_$woodType", "y": 90 },
                    "facing_y_axis=false,bottom=false": { "model": "timbered:block/square_block_high_diagonal_top_$woodType"},

                    "facing_y_axis=true,bottom=true": { "model": "timbered:block/square_block_high_diagonal_bottom_$woodType" },
                    "facing_y_axis=false,bottom=true": { "model": "timbered:block/square_block_high_diagonal_bottom_$woodType", "y": 90 }
                  }
                }
            """.trimIndent()
            )
            println("Generated JSON for blockstates square_block_high_diagonal $woodType: ${it.absolutePath}")
        }
        blocks.forEach { block ->
            File("src/main/resources/assets/timbered/models/item/${block.name}_$woodType.json").let {
                it.writeText("""
                        {
                          "parent": "timbered:block/${block.defaultModelName}_$woodType"
                        }
                """.trimIndent()
                )
                println("Generated JSON for item ${block.name} $woodType model block: ${it.absolutePath}")
            }
        }
    }
}
