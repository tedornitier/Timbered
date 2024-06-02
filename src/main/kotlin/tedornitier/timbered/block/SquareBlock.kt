package tedornitier.timbered.block

import net.minecraft.world.level.block.Block

class SquareBlock : Block(Properties.of().lightLevel { 15 }.strength(3.0f)) {
    companion object {
        val name = "square_block"
    }
}
