package tedornitier.timbered.block

class SquareBlockCross : TimberedBlock() {
    companion object: TimberedObject {
        override val name = "square_block_cross"
        override val defaultModelName = "square_block_cross"
        override val hasLeftRight = false
        override val hasTopBottom = false
        override val recipePattern = """
            #.#
            .#.
            #.#
        """.trimIndent()

        override fun getLocalizedName(locale: String): String {
            return when (locale) {
                "en_us" -> "half-timbered cross block"
                "de_de" -> "Fachwerkkreuzblock"
                "it_it" -> "Blocco a croce a mezza trave"
                else -> TODO()
            }
        }
    }

    override val timberedObject: TimberedObject = Companion
}
