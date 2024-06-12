package tedornitier.timbered.block

class SquareBlockHighDiagonal : TopBottomMirroredBlock() {
    companion object: TimberedObject {
        override val name = "square_block_high_diagonal"
        override val defaultModelName = "square_block_high_diagonal_bottom"
        override val hasLeftRight = true
        override val hasTopBottom = true
        override val recipePattern = """
            .#.
            #..
            ...
        """.trimIndent()

        override fun getLocalizedName(locale: String): String {
            return when (locale) {
                "en_us" -> "half-timbered high diagonal block"
                "de_de" -> "Fachwerkhoherdiagonalblock"
                "it_it" -> "Blocco diagonale alto a mezza trave"
                else -> TODO()
            }
        }
    }

    override val timberedObject: TimberedObject = Companion
}