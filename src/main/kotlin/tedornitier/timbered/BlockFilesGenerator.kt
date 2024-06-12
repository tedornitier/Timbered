package tedornitier.timbered

import tedornitier.timbered.block.TimberedBlocks.blocks
import tedornitier.timbered.block.TimberedObject
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.io.File
import kotlin.reflect.full.companionObjectInstance

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
    woodTypes.forEach { woodType ->
        blocks.forEach { blockClass ->
            val blockData = blockClass.companionObjectInstance as TimberedObject
            if (!blockData.hasLeftRight && !blockData.hasTopBottom) { // TODO replace with enum type
                File("src/main/resources/assets/timbered/models/block/${blockData.name}_$woodType.json").let {
                    it.writeText(createModelBlock(blockPrefix, blockData.name, woodType))
                    println("Generated JSON for simple ${blockData.name} $woodType model block: ${it.absolutePath}")
                }
                File("src/main/resources/assets/timbered/blockstates/${blockData.name}_$woodType.json").let {
                    it.writeText(createBlockState(blockPrefix, blockData.name, woodType))
                    println("Generated JSON for blockstates ${blockData.name} $woodType: ${it.absolutePath}")
                }
            }
            if (blockData.hasLeftRight && !blockData.hasTopBottom) { // TODO replace with enum type
                File("src/main/resources/assets/timbered/models/block/${blockData.name}_$woodType.json").let {
                    it.writeText(createModelBlock(blockPrefix, blockData.name, woodType, Pair("left", "right")))
                    println("Generated JSON for mirrored ${blockData.name} $woodType model block: ${it.absolutePath}")
                }
                File("src/main/resources/assets/timbered/blockstates/${blockData.name}_$woodType.json").let {
                    it.writeText(
                        """
                    {
                      "variants": {
                        "facing_y_axis=true": { "model": "timbered:block/${blockData.name}_$woodType" },
                        "facing_y_axis=false": { "model": "timbered:block/${blockData.name}_$woodType", "y": 90 }
                      }
                    }
                """.trimIndent()
                    )
                    println("Generated JSON for blockstates ${blockData.name} $woodType: ${it.absolutePath}")
                }
            }
            if (blockData.hasLeftRight && blockData.hasTopBottom) { // TODO replace with enum type
                File("src/main/resources/assets/timbered/blockstates/${blockData.name}_$woodType.json").let {
                    it.writeText(
                        """
                            {
                              "variants": {
                                "facing_y_axis=true,bottom=false": { "model": "timbered:block/${blockData.name}_top_$woodType", "y": 90 },
                                "facing_y_axis=false,bottom=false": { "model": "timbered:block/${blockData.name}_top_$woodType"},
            
                                "facing_y_axis=true,bottom=true": { "model": "timbered:block/${blockData.name}_bottom_$woodType" },
                                "facing_y_axis=false,bottom=true": { "model": "timbered:block/${blockData.name}_bottom_$woodType", "y": 90 }
                              }
                            }
                        """.trimIndent()
                    )
                    println("Generated JSON for blockstates ${blockData.name} $woodType: ${it.absolutePath}")
                }
                listOf("top", "bottom").forEach { verticalSide ->
                    File("src/main/resources/assets/timbered/models/block/${blockData.name}_${verticalSide}_$woodType.json").let {
                        it.writeText(createModelBlock(blockPrefix, "${blockData.name}_$verticalSide", woodType, Pair("left", "right")))
                        println("Generated JSON for mirrored ${blockData.name} $verticalSide $woodType model block: ${it.absolutePath}")
                    }
                }
            }
            File("src/main/resources/assets/timbered/models/item/${blockData.name}_$woodType.json").let {
                it.writeText("""
                        {
                          "parent": "timbered:block/${blockData.defaultModelName}_$woodType"
                        }
                """.trimIndent()
                )
                println("Generated JSON for item ${blockData.name} $woodType model block: ${it.absolutePath}")
            }
        }
    }
}
