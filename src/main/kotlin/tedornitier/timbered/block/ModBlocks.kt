package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import tedornitier.timbered.Timbered
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.util.function.Supplier

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    init {
        woodTypes.forEach { wood ->
            REGISTRY.register("${SquareBlock.NAME}_$wood") { -> SquareBlock() }.also {
                BLOCK_ITEMS.register("${SquareBlock.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            REGISTRY.register("${SquareBlockDiagonal.NAME}_$wood") { -> SquareBlockDiagonal() }.also {
                BLOCK_ITEMS.register("${SquareBlockDiagonal.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            REGISTRY.register("${SquareBlockCross.NAME}_$wood") { -> SquareBlockCross() }.also {
                BLOCK_ITEMS.register("${SquareBlockCross.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            REGISTRY.register("${SquareBlockHighDiagonal.NAME}_$wood") { -> SquareBlockHighDiagonal() }.also {
                BLOCK_ITEMS.register("${SquareBlockHighDiagonal.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
        }
    }
}
