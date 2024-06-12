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
    val languages: List<Pair<String, MutableList<String>>> = listOf(
        "en_us" to mutableListOf(),
        "de_de" to mutableListOf(),
        "it_it" to mutableListOf()
    )
    woodTypes.forEach { woodType ->
        blocks.forEach { blockClass ->
            val blockData = blockClass.companionObjectInstance as TimberedObject
            languages.forEach { languagePair ->
                val language = languagePair.first
                val id = "block.timbered.${blockData.name}_$woodType"
                val translation = localizeWoodType(woodType, language, blockData.getLocalizedName(language))
                languagePair.second.add("  \"$id\" : \"$translation\"")
            }

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
                        it.writeText(
                            createModelBlock(
                                blockPrefix,
                                "${blockData.name}_$verticalSide",
                                woodType,
                                Pair("left", "right")
                            )
                        )
                        println("Generated JSON for mirrored ${blockData.name} $verticalSide $woodType model block: ${it.absolutePath}")
                    }
                }
            }
            File("src/main/resources/assets/timbered/models/item/${blockData.name}_$woodType.json").let {
                it.writeText(
                    """
                        {
                          "parent": "timbered:block/${blockData.defaultModelName}_$woodType"
                        }
                """.trimIndent()
                )
                println("Generated JSON for item ${blockData.name} $woodType model block: ${it.absolutePath}")
            }
            File("src/main/resources/data/timbered/recipes/${blockData.name}_$woodType.json").let {
                val material = if (woodType == "bamboo") woodType else woodType + "_planks"
                val pattern =
                    blockData.recipePattern.lines().joinToString(",\n") { craftingLine -> "    \"$craftingLine\"" }
                it.writeText(
                    """{
  "type": "minecraft:crafting_shaped",
  "category": "building_blocks",
  "key": {
    "#": {
      "item": "minecraft:$material"
    },
    ".": {
      "item": "minecraft:white_terracotta"
    }
  },
  "pattern": [
$pattern
  ],
  "result": {
    "id": "timbered:${blockData.name}_$woodType",
    "count": 8
  }
}
"""
                )
                println("Generated JSON for recipe ${blockData.name} $woodType: ${it.absolutePath}")
            }
            File("src/main/resources/data/timbered/advancements/${blockData.name}_$woodType.json").let { // FIXME recipes get unlocked with everything
                val material = if (woodType == "bamboo") woodType else woodType + "_planks"
                it.writeText(
                    """{
  "parent": "minecraft:recipes/root",
  "criteria": {
    "has_$material": {
      "trigger": "minecraft:inventory_changed",
      "conditions": {
        "items": [
          {
            "item": "minecraft:$material"
          }
        ]
      }
    },
    "has_white_terracotta": {
      "trigger": "minecraft:inventory_changed",
      "conditions": {
        "items": [
          {
            "item": "minecraft:white_terracotta"
          }
        ]
      }
    }
  },
  "requirements": [
    ["has_$material", "has_white_terracotta"]
  ],
  "rewards": {
    "recipes": [
      "timbered:${blockData.name}_$woodType"
    ]
  }
}
"""
                )
                println("Generated JSON for recipe ${blockData.name} $woodType: ${it.absolutePath}")
            }
        }
    }
    languages.forEach { languagePair ->
        val language = languagePair.first
        File("src/main/resources/assets/timbered/lang/$language.json").let {
            it.writeText(
                """{
${languagePair.second.joinToString(",\n")}
}""".trimIndent()
            )
            println("Generated JSON for language $language: ${it.absolutePath}")
        }
    }
}

fun localizeWoodType(woodType: String, language: String, localizedName: String): String {
    return when (language) {
        "en_us" -> when (woodType) {
            "oak" -> "Oak"
            "birch" -> "Birch"
            "spruce" -> "Spruce"
            "jungle" -> "Jungle"
            "acacia" -> "Acacia"
            "dark_oak" -> "Dark oak"
            "bamboo" -> "Bamboo"
            "cherry" -> "Cherry"
            "crimson" -> "Crimson"
            "warped" -> "Warped"
            "mangrove" -> "Mangrove"
            else -> throw IllegalArgumentException("Unknown wood type: $woodType")
        } + " $localizedName"

        "de_de" -> when (woodType) {
            "oak" -> "Eichen"
            "birch" -> "Birken"
            "spruce" -> "Fichten"
            "jungle" -> "Tropen"
            "acacia" -> "Akazien"
            "dark_oak" -> "Schwarzeichen"
            "bamboo" -> "Bambus"
            "cherry" -> "Kirsch"
            "crimson" -> "Karmesin"
            "warped" -> "Wirrholz"
            "mangrove" -> "Mangroven"
            else -> throw IllegalArgumentException("Unknown wood type: $woodType")
        } + "-$localizedName"

        "it_it" -> "$localizedName " + when (woodType) {
            "oak" -> "di quercia"
            "birch" -> "di betulla"
            "spruce" -> "di abete"
            "jungle" -> "della giungla"
            "acacia" -> "di acacia"
            "dark_oak" -> "di quercia scura"
            "bamboo" -> "di bambù"
            "cherry" -> "di ciliegio"
            "crimson" -> "cremisi"
            "warped" -> "distorto"
            "mangrove" -> "di mangrovia"
            else -> throw IllegalArgumentException("Unknown wood type: $woodType")
        }

        else -> throw IllegalArgumentException("Unknown language: $language")
    }
}
