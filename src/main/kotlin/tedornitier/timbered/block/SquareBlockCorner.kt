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
import tedornitier.timbered.VerticalPosition

class SquareBlockCorner : Block(Properties.of().strength(3.0f)) {
    companion object {
        const val NAME = "square_block_corner"
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val POSITION: EnumProperty<VerticalPosition> = EnumProperty.create("position", VerticalPosition::class.java)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POSITION, VerticalPosition.BOTTOM)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING).add(POSITION)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facing = context.horizontalDirection.opposite
        val belowState = context.level.getBlockState(context.clickedPos.below())
        val aboveState = context.level.getBlockState(context.clickedPos.above())
        return this.defaultBlockState()
            .setValue(FACING, facing)
            .setVerticalPositionState(aboveState, belowState)
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
        return state
            .setVerticalPositionState(aboveState, belowState)
    }

    private fun BlockState.setVerticalPositionState(above: BlockState, below: BlockState): BlockState {
        return if (isConnected(below)) {
            if (!isConnected(above)) {
                setValue(POSITION, VerticalPosition.TOP)
            } else {
                setValue(POSITION, VerticalPosition.MIDDLE)
            }
        } else {
            setValue(POSITION, VerticalPosition.BOTTOM)
        }
    }

    private fun isConnected(state: BlockState): Boolean {
        return state.block is SquareBlockCorner || state.block is SquareBlockOne || state.block is SquareBlock
    }
}
