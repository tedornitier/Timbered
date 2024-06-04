package tedornitier.timbered.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import tedornitier.timbered.HorizontalPosition
import tedornitier.timbered.VerticalPosition

class SquareBlockHighDiagonal : Block(Properties.of().strength(3.0f)) {
    companion object {
        const val NAME = "square_block_high_diagonal"
        const val DEFAULT_BOTTOM_STATE = true
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val BOTTOM: BooleanProperty = BooleanProperty.create("bottom")
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BOTTOM, DEFAULT_BOTTOM_STATE)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING).add(BOTTOM)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facing = context.horizontalDirection.opposite
        val belowBlockState = context.level.getBlockState(context.clickedPos.below())
        return this.defaultBlockState()
            .setValue(FACING, facing)
            .setValue(BOTTOM, getOppositeState(belowBlockState))
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val belowBlockState = level.getBlockState(pos.below())
        return state.setValue(BOTTOM, getOppositeState(belowBlockState))
    }

    private fun getOppositeState(blockState: BlockState): Boolean {
        return if (blockState.block is SquareBlockHighDiagonal) !blockState.getValue(BOTTOM)
            else DEFAULT_BOTTOM_STATE
    }
}
