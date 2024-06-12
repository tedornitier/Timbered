package tedornitier.timbered.block

class SquareBlock : TimberedBlock() {
    companion object: TimberedObject {
        override val name = "square_block"
        override val defaultModelName = "square_block"
        override val hasLeftRight = false
        override val hasTopBottom = false
        override val recipePattern = """
            ###
            #.#
            ###
        """.trimIndent()

        override fun getLocalizedName(locale: String): String {
            return when (locale) {
                "en_us" -> "half-timbered block"
                "de_de" -> "Fachwerkblock"
                "it_it" -> "Blocco a mezza trave"
                else -> TODO()
            }
        }
    }

    override val timberedObject: TimberedObject = Companion
}
