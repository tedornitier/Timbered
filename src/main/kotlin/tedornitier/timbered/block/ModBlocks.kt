package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import tedornitier.timbered.Timbered
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredRegister

// THIS LINE IS REQUIRED FOR USING PROPERTY DELEGATES
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)

    // If you get an "overload resolution ambiguity" error, include the arrow at the start of the closure.
    val SQUARE_TILE by REGISTRY.register("square_tile") { ->
        Block(BlockBehaviour.Properties.of().lightLevel { 15 }.strength(3.0f))
    }

    // Register block items
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    val SQUARE_TILE_ITEM by BLOCK_ITEMS.register("square_tile") { ->
        BlockItem(SQUARE_TILE, Item.Properties())
    }
}
