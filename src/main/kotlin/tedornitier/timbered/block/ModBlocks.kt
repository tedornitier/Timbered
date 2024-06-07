package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import tedornitier.timbered.Timbered
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import java.util.function.Supplier

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Timbered.ID)

    // Register the custom blocks
    val SQUARE_BLOCK by REGISTRY.register(SquareBlock.NAME) { ->
        SquareBlock()
    }
    val SQUARE_BLOCK_DIAGONAL by REGISTRY.register(SquareBlockDiagonal.NAME) { ->
        SquareBlockDiagonal()
    }
    val SQUARE_BLOCK_CROSS by REGISTRY.register(SquareBlockCross.NAME) { ->
        SquareBlockCross()
    }
    val SQUARE_BLOCK_HIGH_DIAGONAL by REGISTRY.register(SquareBlockHighDiagonal.NAME) { ->
        SquareBlockHighDiagonal()
    }

    // Register block items
    val BLOCK_ITEMS = DeferredRegister.createItems(Timbered.ID)

    init {
        BLOCK_ITEMS.run {
            register(SquareBlock.NAME, Supplier { BlockItem(SQUARE_BLOCK, Item.Properties()) })
            register(SquareBlockDiagonal.NAME, Supplier { BlockItem(SQUARE_BLOCK_DIAGONAL, Item.Properties()) })
            register(SquareBlockCross.NAME, Supplier { BlockItem(SQUARE_BLOCK_CROSS, Item.Properties()) })
            register(SquareBlockHighDiagonal.NAME, Supplier { BlockItem(SQUARE_BLOCK_HIGH_DIAGONAL, Item.Properties()) })
        }
    }
}
