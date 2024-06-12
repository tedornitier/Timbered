package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import tedornitier.timbered.Timbered
import tedornitier.timbered.block.TimberedBlocks.blocks
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.util.function.Supplier

object ModBlocks {
    val registry: DeferredRegister.Blocks = DeferredRegister.createBlocks(Timbered.ID)
    val blockItems: DeferredRegister.Items = DeferredRegister.createItems(Timbered.ID)

    init {
        woodTypes.forEach { wood ->
            blocks.forEach { block ->
                registry.register("${block.name}_$wood") { -> SquareBlock() }.also {
                    blockItems.register("${block.name}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
                }
            }
        }
    }
}
