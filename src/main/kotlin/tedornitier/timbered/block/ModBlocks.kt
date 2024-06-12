package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import tedornitier.timbered.Timbered
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.util.function.Supplier

object ModBlocks {
    val registry: DeferredRegister.Blocks = DeferredRegister.createBlocks(Timbered.ID)
    val blockItems: DeferredRegister.Items = DeferredRegister.createItems(Timbered.ID)

    init {
        woodTypes.forEach { wood ->
            registry.register("${SquareBlock.NAME}_$wood") { -> SquareBlock() }.also {
                blockItems.register("${SquareBlock.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            registry.register("${SquareBlockDiagonal.NAME}_$wood") { -> SquareBlockDiagonal() }.also {
                blockItems.register("${SquareBlockDiagonal.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            registry.register("${SquareBlockCross.NAME}_$wood") { -> SquareBlockCross() }.also {
                blockItems.register("${SquareBlockCross.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
            registry.register("${SquareBlockHighDiagonal.NAME}_$wood") { -> SquareBlockHighDiagonal() }.also {
                blockItems.register("${SquareBlockHighDiagonal.NAME}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
            }
        }
    }
}
