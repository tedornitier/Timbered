package tedornitier.timbered.block

class SquareBlockHighDiagonal : TopBottomMirroredBlock() {
    companion object: TimberedObject {
        override val name = "square_block_high_diagonal"
        override val defaultModelName = "square_block_high_diagonal_bottom"
        override val hasLeftRight = true
        override val hasTopBottom = true
    }

    override val timberedObject: TimberedObject = Companion
}