package tedornitier.timbered.block

class SquareBlockDiagonal : MirroredBlock() {
    companion object: TimberedObject {
        override val name = "square_block_diagonal"
        override val defaultModelName = "square_block_diagonal"
        override val hasLeftRight = true
        override val hasTopBottom = false
        override val recipePattern = """
            ..#
            .#.
            #..
        """.trimIndent()

        override fun getLocalizedName(locale: String): String {
            return when (locale) {
                "en_us" -> "half-timbered diagonal block"
                "de_de" -> "Fachwerkdiagonalblock"
                "it_it" -> "Blocco diagonale a mezza trave"
                else -> TODO()
            }
        }
    }

    override val timberedObject: TimberedObject = Companion
}
