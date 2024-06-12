package tedornitier.timbered.block

class SquareBlockCross : TimberedBlock() {
    companion object: TimberedObject {
        override val name = "square_block_cross"
        override val defaultModelName = "square_block_cross"
        override val hasLeftRight = false
        override val hasTopBottom = false
    }

    override val timberedObject: TimberedObject = Companion
}
