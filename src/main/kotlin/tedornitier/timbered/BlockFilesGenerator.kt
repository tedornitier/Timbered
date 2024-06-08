package tedornitier.timbered

import java.io.File

val woodTypes =
    listOf("oak") // TODO, "birch", "spruce", "jungle", "acacia", "dark_oak", "bamboo", "cherry", "crimson", "warped", "mangrove")

fun createModelBlock(
    prefix: String,
    blockName: String,
    woodType: String,
    sides: Pair<String, String>? = null
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
    val mirroredTextureBlockNames =
        listOf("square_block_diagonal", "square_block_high_diagonal_bottom", "square_block_high_diagonal_top")
    woodTypes.forEach { woodType ->
        sameTextureBlockNames.forEach { blockName ->
            File("src/main/resources/assets/timbered/models/block/${blockName}_$woodType.json").let {
                it.writeText(createModelBlock(blockPrefix, blockName, woodType))
                println("Generated JSON for simple $blockName $woodType model block: ${it.absolutePath}")
            }
        }
        mirroredTextureBlockNames.forEach { blockName ->
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
    }
}
