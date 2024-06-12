package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import tedornitier.timbered.Timbered
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import java.util.function.Supplier

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    init {
        BLOCK_ITEMS.run {
            woodTypes.forEach { wood ->
                val SQUARE_BLOCK by REGISTRY.register("${SquareBlock.NAME}_$wood") { -> SquareBlock() }
                val SQUARE_BLOCK_DIAGONAL by REGISTRY.register("${SquareBlockDiagonal.NAME}_$wood") { -> SquareBlockDiagonal() }
                val SQUARE_BLOCK_CROSS by REGISTRY.register("${SquareBlockCross.NAME}_$wood") { -> SquareBlockCross() }
                val SQUARE_BLOCK_HIGH_DIAGONAL by REGISTRY.register("${SquareBlockHighDiagonal.NAME}_$wood") { -> SquareBlockHighDiagonal() }

                register("${SquareBlock.NAME}_$wood", Supplier { BlockItem(SQUARE_BLOCK, Item.Properties()) })
                register("${SquareBlockDiagonal.NAME}_$wood", Supplier { BlockItem(SQUARE_BLOCK_DIAGONAL, Item.Properties()) })
                register("${SquareBlockCross.NAME}_$wood", Supplier { BlockItem(SQUARE_BLOCK_CROSS, Item.Properties()) })
                register("${SquareBlockHighDiagonal.NAME}_$wood", Supplier { BlockItem(SQUARE_BLOCK_HIGH_DIAGONAL, Item.Properties()) })
            }
        }
    }
}
