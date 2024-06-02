package tedornitier.timbered.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import tedornitier.timbered.HorizontalPosition
import tedornitier.timbered.VerticalPosition

class SquareBlockCorner : Block(Properties.of().strength(3.0f)) {
    companion object {
        const val NAME = "square_block_corner"
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val VERTICAL_POSITION: EnumProperty<VerticalPosition> = EnumProperty.create("vertical_position", VerticalPosition::class.java)
        val HORIZONTAL_POSITION: EnumProperty<HorizontalPosition> = EnumProperty.create("horizontal_position", HorizontalPosition::class.java)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(VERTICAL_POSITION, VerticalPosition.BOTTOM)
                .setValue(HORIZONTAL_POSITION, HorizontalPosition.LEFT)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING).add(VERTICAL_POSITION).add(HORIZONTAL_POSITION)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facing = context.horizontalDirection.opposite
        val belowState = context.level.getBlockState(context.clickedPos.below())
        val aboveState = context.level.getBlockState(context.clickedPos.above())
        val rightState = context.level.getBlockState(context.clickedPos.relative(facing.clockWise))
        val leftState = context.level.getBlockState(context.clickedPos.relative(facing.counterClockWise))
        return this.defaultBlockState()
            .setValue(FACING, facing)
            .setVerticalPositionState(aboveState, belowState)
            .setHorizontalPositionState(rightState, leftState)
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val aboveState = level.getBlockState(pos.above())
        val belowState = level.getBlockState(pos.below())
        val rightState = level.getBlockState(pos.relative(state.getValue(FACING).clockWise))
        val leftState = level.getBlockState(pos.relative(state.getValue(FACING).counterClockWise))
        return state
            .setVerticalPositionState(aboveState, belowState)
            .setHorizontalPositionState(rightState, leftState)
    }

    private fun BlockState.setVerticalPositionState(above: BlockState, below: BlockState): BlockState {
        return setValue(
            VERTICAL_POSITION, if (isConnected(below)) {
                if (!isConnected(above)) { VerticalPosition.TOP }
                else { VerticalPosition.MIDDLE }
            } else { VerticalPosition.BOTTOM }
        )
    }

    private fun BlockState.setHorizontalPositionState(right: BlockState, left: BlockState): BlockState {
        return setValue(
            HORIZONTAL_POSITION, if (isConnected(right)) {
                if (!isConnected(left)) { HorizontalPosition.RIGHT }
                else { HorizontalPosition.MIDDLE }
            } else { HorizontalPosition.LEFT }
        )
    }

    private fun isConnected(state: BlockState): Boolean {
        return state.block is SquareBlock || state.block is SquareBlockCorner
    }
}
