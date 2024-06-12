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
    }

    override val timberedObject: TimberedObject = Companion
}
