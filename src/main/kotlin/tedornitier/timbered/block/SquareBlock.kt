package tedornitier.timbered.block

class SquareBlock : TimberedBlock() {
    companion object: TimberedObject {
        override val name = "square_block"
        override val defaultModelName = "square_block"
        override val hasLeftRight = false
        override val hasTopBottom = false
    }

    override val timberedObject: TimberedObject = Companion
}
