package tedornitier.timbered.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty


class SquareBlockCorner : Block(Properties.of().strength(3.0f)) {
    companion object {
        const val NAME = "square_block_corner"
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facing = context.horizontalDirection.opposite
        return this.defaultBlockState()
            .setValue(FACING, facing)
    }


    private fun isConnected(state: BlockState): Boolean {
        return state.block is SquareBlockCorner || state.block is SquareBlockOne || state.block is SquareBlock
    }
}
