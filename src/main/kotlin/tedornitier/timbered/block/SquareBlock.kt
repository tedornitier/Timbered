package tedornitier.timbered.block

import net.minecraft.world.level.block.Block

class SquareBlock : Block(Properties.of().strength(3.0f)) {
    companion object {
        const val NAME = "square_block"
    }
}
