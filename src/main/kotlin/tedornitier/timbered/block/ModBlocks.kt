package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import tedornitier.timbered.Timbered
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)

    // Register the custom blocks
    val SQUARE_BLOCK by REGISTRY.register(SquareBlock.NAME) { ->
        SquareBlock()
    }
    val SQUARE_BLOCK_CORNER by REGISTRY.register(SquareBlockCorner.NAME) { ->
        SquareBlockCorner()
    }
    val SQUARE_BLOCK_DIAGONAL by REGISTRY.register(SquareBlockDiagonal.NAME) { ->
        SquareBlockDiagonal()
    }

    // Register block items
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    val SQUARE_ITEM by BLOCK_ITEMS.register(SquareBlock.NAME) { ->
        BlockItem(SQUARE_BLOCK, Item.Properties())
    }
    val SQUARE_CORNER_ITEM by BLOCK_ITEMS.register(SquareBlockCorner.NAME) { ->
        BlockItem(SQUARE_BLOCK_CORNER, Item.Properties())
    }
    val SQUARE_DIAGONAL_ITEM by BLOCK_ITEMS.register(SquareBlockDiagonal.NAME) { ->
        BlockItem(SQUARE_BLOCK_DIAGONAL, Item.Properties())
    }
}
