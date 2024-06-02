package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import tedornitier.timbered.Timbered
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)

    // Register the custom block
    val SQUARE_TILE by REGISTRY.register(SquareBlock.name) { ->
        SquareBlock()
    }

    // Register block items
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    val SQUARE_TILE_ITEM by BLOCK_ITEMS.register(SquareBlock.name) { ->
        BlockItem(SQUARE_TILE, Item.Properties())
    }
}
